from numpy import zeros
from scipy.linalg import svd
#following needed for TFIDF
from math import log, sqrt, pow, acos, pi
from numpy import asarray, sum
import psycopg2
import itertools
import gc
import nltk, re, pprint
import os, sys 
import re
from unicodedata import normalize


#from nltk import word_tokenize
from nltk import word_tokenize
from nltk.stem import SnowballStemmer
from textclean.textclean import textclean

sys.getdefaultencoding()

stemmer = SnowballStemmer('spanish')
 
def remover_tilde(desc, codif='utf-8'):    
        return normalize('NFKD', desc.decode(codif)).encode('ASCII','ignore')


try:
    conn = psycopg2.connect("dbname='vm31ene2014' user='postgres' host='localhost' password='123456'")
except:
    print "I am unable to connect to the database"

cur = conn.cursor()
cur.execute("""SELECT nombre from licitacion order by id limit 1000""")
t = cur.fetchall()

titles = [i[0] for i in t]
print "lee las licitaciones"


for i in range(len(titles)):
    #titles[i] = titles[i].lower().decode('ascii')
    titles[i] = remover_tilde(titles[i].lower())

    #titles[i] = titles[i].decode('utf-8').encode('utf-8').lower()
    #text = textclean.clean(titles[i].lower())
    #tokens = nltk.wordpunct_tokenize(titles[i].lower())
    #palabras = nltk.Text(tokens)
    #print tokens
    #text = 'En su parte de arriba encontramos la ";zona de mandos";, donde se puede echar el detergente, aunque en nuestro caso lo al ser gel lo ponemos directamente junto con la ropa.'
    #stemmed_text = [stemmer.stem(i) for i in word_tokenize(text)]
   #opinion = [[spanish_stemmer.stem(word) for word in sentence.split(" ")]for sentence in opinion]
    #print stemmed_text
    #titles[i] = stemmed_text

    #titles[i] = titles[i].lower()
    #token = nltk.word_tokenize(titles[i])
    #print token

text_file = open("C:\Documents and Settings\Anibal\Escritorio\LSA\stopwords.txt", "r")
stopwords = text_file.read().split('\n')
ignorechars = ''',:'!/() []'''

class LSA(object):
    def __init__(self, stopwords, ignorechars):
        self.stopwords = stopwords
        self.titles = titles
        self.ignorechars = ignorechars
        self.wdict = {}
        self.dcount = 0
                
    def parse(self, doc):
        words = doc.split();
        for w in words:
            w = w.lower().translate(None, self.ignorechars)
            #tokens = nltk.wordpunct_tokenize(w)
            #w = nltk.text(tokens)
            w = stemmer.stem(w)
            if w in self.stopwords:
                continue
            elif w in self.wdict:
                self.wdict[w].append(self.dcount)
            else:
                self.wdict[w] = [self.dcount]
        self.dcount += 1      
    def build(self):
        self.keys = [k for k in self.wdict.keys() if len(self.wdict[k]) > 1]
        self.keys.sort()
        self.A = zeros([len(self.keys), self.dcount])
        for i, k in enumerate(self.keys):
            for d in self.wdict[k]:
                self.A[i,d] += 1
    def calc(self):
        self.U, self.S, self.Vt = svd(self.A)
    def TFIDF(self):
        WordsPerDoc = sum(self.A, axis=0)        
        DocsPerWord = sum(asarray(self.A > 0, 'i'), axis=1)
        rows, cols = self.A.shape
        for i in range(rows):
            for j in range(cols):
                self.A[i,j] = (self.A[i,j] / WordsPerDoc[j]) * log(float(cols) / DocsPerWord[i])

    def cosineWords(self):

        word1 = raw_input("Ingrese una palabra: ")
        word2 = raw_input("Ingrese otra palabra: ")

        posicion1 = -1
        posicion2 = -1

        for i in range(len(self.keys)):
            if (word1 == self.keys[i]):
                posicion1 = i
            elif (word2 == self.keys[i]):
                posicion2 = i

        if (posicion1 != -1) or (posicion2 != -1):
            self.vector1 = [-1*self.U[posicion1,1], -1*self.U[posicion1,2]]
            self.vector2 = [-1*self.U[posicion2,1], -1*self.U[posicion2,2]]

            print 'vector', word1, '\t\t', self.vector1
            print 'vector', word2, '\t',self.vector2

            producto = (self.vector1[0]*self.vector2[0]) + (self.vector1[1]*self.vector2[1])

            valor1 = sqrt(pow(self.vector1[0], 2) +pow(self.vector1[1], 2))
            valor2 = sqrt(pow(self.vector2[0], 2) +pow(self.vector2[1], 2))
            cosine = producto / (valor1*valor2)
            arcCosRad = acos(cosine)
            arcCosine = 180*arcCosRad/pi

            print 'coseno del angulo entre ambos vectores', cosine
            print 'angulo entre ambos vectores', arcCosine

        else:
            print 'Ninguna palabra figura en la lista'
            print 'No se puede calcular ni el coseno ni su angulo ya que ninguna de las palabras ingresadas figura en la lista'

    def cosineLicitacion(self):

        licitacion1 = input("Ingrese una licitacion: ")
        licitacion2 = input("Ingrese otra licitacion: ")

        posicion1 = (licitacion1-1)
        posicion2 = (licitacion2-1)

        self.vector1 = [-1*self.Vt[1,posicion1], -1*self.Vt[2,posicion1]]
        self.vector2 = [-1*self.Vt[1,posicion2], -1*self.Vt[2,posicion2]]

        print 'vector d', licitacion1, '\t\t', self.vector1
        print 'vector d', licitacion2, '\t',self.vector2

        producto = (self.vector1[0]*self.vector2[0]) + (self.vector1[1]*self.vector2[1])

        valor1 = sqrt(pow(self.vector1[0], 2) +pow(self.vector1[1], 2))
        valor2 = sqrt(pow(self.vector2[0], 2) +pow(self.vector2[1], 2))
        cosine = producto / (valor1*valor2)
        arcCosRad = acos(cosine)
        arcCosine = 180*arcCosRad/pi

        print 'coseno del angulo entre ambos vectores', cosine
        print 'angulo entre ambos vectores', arcCosine


    def printA(self):
        print 'Here is the count matrix'

        for i in range(len(self.A)):
            if(len(self.keys[i]) < 7):
                print self.keys[i], '\t\t', self.A[i]
            else:
                print self.keys[i], '\t', self.A[i]

    def printSVD(self):
        print 'Here are the singular values'
        print self.S
        print 'Here are the first 3 columns of the U matrix'
        for i in range(len(self.A)):
            if(len(self.keys[i]) < 7):
                print self.keys[i], '\t\t', -1*self.U[i, 0:3]
            else:
                print self.keys[i], '\t', -1*self.U[i, 0:3]
        print 'Here are the first 3 rows of the Vt matrix'
        print -1*self.Vt[0:3, :]
        print '\n\n'
 

mylsa = LSA(stopwords, ignorechars)
for t in titles:
    mylsa.parse(t)

gc.collect()
mylsa.build()

gc.collect()
mylsa.calc()

gc.collect()
mylsa.printSVD()

gc.collect()
mylsa.cosineWords()

gc.collect()
mylsa.cosineLicitacion()
gc.collect()