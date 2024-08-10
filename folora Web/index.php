<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>ƒolora</title>
	
</head>
<body>
 
<section class="box">  

<h1>Folora</h1>
<p class="desc">A mobile app to find drivers for various trips or transportation.</p>
<div class="animatedtxt">
<div class="our"></div>
<div>
 
   
</div>
</div>
<a href="login.php" class="boxBtn">Get Started! (Admin)</a>

              <center> <p class="group"  style="position: absolute;bottom: 0;left: 0; margin:10px;text-align:center; font-size:small">© Developed by group 47 </p></center>

</section>
<style>


* {
margin: 0;
padding: 0;
box-sizing: border-box;

}
body {
font-family: poppins;
overflow:hidden;


}
h1{
font-family: 'Rubik Glitch', cursive;
font-weight: bolder;
text-shadow: #999;


}

section {
display: flex;
flex-direction: column;
align-items: center;
padding: 110px 100px;
}

header {
position: absolute;
top: 0;
left: 0;
z-index: 10;
width: 100%;
display: flex;
justify-content: space-between;
align-items: center;
color: #fff;
padding: 35px 100px 0;
}
header a {
text-transform: capitalize;
font-weight: 900;
}
header img {
width: 100px;
}



.box {
position: relative;
justify-content: center;
min-height: 100vh;
color: #fff;
text-align: center;
background: linear-gradient(to bottom, rgba(43, 43, 43, 0.3) 0%, rgba(32, 31, 31, 0.7) 85%, rgb(22, 21, 21) 100%,url('assets/img/bg.jpg'));
}
.box video {
position: absolute;
left: 0;
top: 0;
width: 100%;
height: 100%;
object-fit: cover;
z-index: -1;
}
.box h1 {
margin-bottom: -15px;
font-size: 65px;
text-transform: uppercase;
text-shadow: 0 2px 2px rgba(0, 0, 0, 0.5);
}
.box h3 {
margin-bottom: 40px;
font-size: 25px;
}
.box a.boxBtn {
padding: 15px 35px;
background: transparent;
border-radius: 50px;
color: #fff;
text-decoration: none;
text-style:bold;
text-transform: uppercase;
margin-top: 30px;
border: 1px solid #fff;
transition: all .5s;
}
.box a.boxBtn:hover {
background: #fff;
color: #000;
}
@media (max-width: 800px) {
.box {
min-height: 600px;
}
.box h1 {
font-size: 42px;
}
.box h3 {
font-size: 20px;
}
.box a.boxBtn {
padding: 15px 40px;
}
}

div {
display:inline-block;
overflow:hidden;
white-space:nowrap;

  }
 
 
 
  #group {
font-size:11px;
color:skyblue;
margin-top:120px;
  }

  .animatedtxt
  {
margin-top: -30px;
margin-bottom: 10px;
margin-left: 80px;
font-family: 'Nerko One', cursive;
font-size: 16px;
}

#desc{
	font-size:14px;
color:skyblue;
text-align: center;
padding: 0px;

}



 

</style>
</body>
</html>