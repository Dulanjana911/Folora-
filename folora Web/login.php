<!DOCTYPE html>
<html>
<head>
	<title> Login | Folora</title>
	<link rel="stylesheet" type="text/css" href="assets/css/login.css">
	<link href="https://fonts.googleapis.com/css?family=Poppins:600&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/a81368914c.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>


</head>
<body>
	<img class="wave" src="assets/img/slide.png">
	<div class="container">
		<div class="img">
		
		</div>
		<div class="login-content">
			<form action="" name="myForm" method="post">
				<img src="https://api.dicebear.com/5.x/miniavs/svg?seed=Abby">
				<h2 class="title">Welcome</h2>
           		<div class="input-div one">
           		   <div class="i">
           		   		<i class="fas fa-user"></i>
           		   </div>
           		   <div class="div">
           		   		<h5>Username</h5>
           		   		<input type="text" name="logname" class="input">
           		   </div>
           		</div>
           		<div class="input-div pass">
           		   <div class="i"> 
           		    	<i class="fas fa-lock"></i>
           		   </div>
           		   <div class="div">
           		    	<h5>Password</h5>
           		    	<input type="password" name="logpass" class="input">
            	   </div>
            	</div>
            
            	<input type="submit" class="btn" name="btnLog" value="Login">
            </form>
        </div>
    </div>
    <script type="text/javascript" src="assets/js/app.js"></script>

	<script>

const form = document.querySelector('form[name="myForm"]');
	const usernameInput = form.elements.logname;
	const passwordInput = form.elements.logpass;

	form.addEventListener('submit', function(e) {
		e.preventDefault(); // prevent form submission

		const username = usernameInput.value.trim();
		const password = passwordInput.value.trim();

		if (username === 'Admin' && password === '1234') {
			// Redirect to another page
			window.location.href = 'dashboard.php';
		} else {
			alert('Invalid username or password.');
		}
	});

	</script>

  
</body>
</html>