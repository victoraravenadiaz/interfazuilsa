<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->  
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->  
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->  
<head>
    <title>Free Bootstrap Theme for Developers</title>
    <!-- Meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">    
    <link rel="shortcut icon" href="favicon.ico">  
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,300italic,400italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'> 
    <!-- Global CSS -->
    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
    <!-- Plugins CSS -->    
    <link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="assets/plugins/prism/prism.css">
    <!-- Theme CSS -->  
    <link id="theme-style" rel="stylesheet" href="assets/css/styles.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head> 

<body data-spy="scroll">
    
    <!---//Facebook button code-->
    <div id="fb-root"></div>
    <script>(function(d, s, id) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) return;
      js = d.createElement(s); js.id = id;
      js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.0";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>
    
    <!-- ******HEADER****** --> 
    <header id="header" class="header">  
        <div class="container">            
            <h1 class="logo pull-left">
                <a class="scrollto" href="#promo">
                    <span class="logo-title">LicitLSA</span>
                </a>
            </h1><!--//logo-->              
        </div>
    </header><!--//header-->


    <!-- ******ABOUT****** --> 
    <section id="about" class="about section">
        <div class="container">
            <h2 class="title text-center">Ingresar Licitación</h2>
            <div class="row">
                    <form id="contact_form" align="center" enctype="multipart/form-data">
                    <div class="row">
                        <label for="title" class="lead">Título:</label><br />
                        <input id="title" class="input" name="title" type="text" value="" size="30" style="color:#000000;"/><br />
                    </div>
                    <div class="row">
                        <label for="desc" class="lead">Descripción:</label><br />
                        <textarea id="desc" class="input" name="desc" rows="7" cols="30" style="color:#000000;"/></textarea><br />
                    </div>
                    <div class="row">
                        <label for="prods" class="lead">Productos/Servicios:</label><br />
                        <textarea id="prods" class="input" name="prods" rows="7" cols="30" style="color:#000000;"></textarea><br />
                    </div>
                    <div class="row">
                        <label for="region" class="lead">Región:</label><br />
                        <select rows="7">
                            <option value="arica">Arica y Parinacota</option>
                            <option value="tarapaca">Tarapacá</option>
                            <option value="antofagasta">Antofagasta</option>
                            <option value="atacama">Atacama</option>
                            <option value="coquimbo">Coquimbo</option>
                            <option value="valparaiso">Valparaíso</option>
                            <option value="metropolitana">Metropolitana</option>
                            <option value="ohiggins">O'Higgins</option>
                            <option value="maule">Maule</option>
                            <option value="biobio">Bío Bío</option>
                            <option value="araucania">Araucanía</option>
                            <option value="losrios">Los Ríos</option>
                            <option value="loslagos">Los Lagos</option>
                            <option value="aysen">Aysén</option>
                            <option value="magallanes">Magallanes</option>
                        </select>
                    </div>
                    <div class="row">
                        <label for="monto" class="lead">Monto:</label><br />
                        <select rows="7">
                            <option value="volvo">Menor a 100 UTM</option>
                            <option value="saab">Más de 100 UTM</option>
                            <option value="mercedes">Menor a 1000 UTM</option>
                            <option value="audi">Más de 100 UTM</option>
                        </select>
                    </div><br >
                    <input id="ingresar" type="submit" value="Ingresar" class="btn btn-dark btn-lg" />
                </form>           
            </div><!--//row-->            
        </div><!--//container-->
    </section><!--//about-->
     
    <!-- Javascript -->          
    <script type="text/javascript" src="assets/plugins/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="assets/plugins/jquery-migrate-1.2.1.min.js"></script>    
    <script type="text/javascript" src="assets/plugins/jquery.easing.1.3.js"></script>   
    <script type="text/javascript" src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>     
    <script type="text/javascript" src="assets/plugins/jquery-scrollTo/jquery.scrollTo.min.js"></script> 
    <script type="text/javascript" src="assets/plugins/prism/prism.js"></script>    
    <script type="text/javascript" src="assets/js/main.js"></script>       
</body>
</html> 

