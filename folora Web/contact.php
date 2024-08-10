<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
      integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
      crossorigin="anonymous"
    />
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>Contact Us</title>
  </head>
  <body>
  <header>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
      <a class="navbar-brand" href="dashboard.php">ƒolora</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item ">
            <a class="nav-link" href="dashboard.php">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="Chat.php">Messages</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="about.php">About</a>
          </li>
          <li class="nav-item active">
            <a class="nav-link" href="contact.php">Contact<span class="sr-only">(current)</span></a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</header>
    <div class="container my-5">
      <h1 class="text-center">Contact Us</h1>
      <hr />
      <div class="row">
        <div class="col-md-6">
          <h3>Get in Touch</h3>
          <form method="post">
            <div class="form-group">
              <label for="name">Name:</label>
              <input type="text" class="form-control" name="name" id="name" />
            </div>
            <div class="form-group">
              <label for="email">Email:</label>
              <input type="email" class="form-control" name="email" id="email" />
            </div>
            <div class="form-group">
              <label for="message">Message:</label>
              <textarea class="form-control" name="message" rows="5"></textarea>
            </div>
            <button type="submit" name="msg-submit" class="btn btn-primary">
              Submit
            </button>
          </form>
        </div>
        <div class="col-md-6">
          <h3>Contact Information</h3>
          <p>
            <i class="fas fa-map-marker-alt mr-3"></i>112 /A Homagama Sri Lanka
          </p>
          <p>
            <i class="fas fa-phone mr-3"></i>+94 752056898
          </p>
          <p>
            <i class="fas fa-envelope mr-3"></i>folora2000@gmail.com
          </p>
        </div>
      </div>
    </div>
  </body>
  <?php 
  
  include 'config.php';

$email = mysqli_real_escape_string($DbConnection,$_POST['email']);
$msg = mysqli_real_escape_string($DbConnection,$_POST['message']);
$name = mysqli_real_escape_string($DbConnection,$_POST['name']);


$sql = "INSERT INTO tbl_contact(email,msg,name)VALUES('$email','$msg','$name')";

if(isset($_POST['msg-submit']))
{
   
  if(mysqli_query($DbConnection,$sql))
  {
    echo '<script>
    
    swal.fire({
      title: "Good job!",
      text: "Your message send success.",
      icon: "success",
      button: "Ok",
    });
    
    
    
    </script>';
  }
  else  echo '<script>
    
  Swal.fire({
    icon: "error",
    title: "Oops...",
    text: "Message send Faild!",
  
  })
  
  
  
  </script>';
  
}
  
  ?>
</html>
