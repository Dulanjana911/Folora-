<?php
include("config.php");
include("firebaseRDB.php");

$db = new firebaseRDB($databaseURL);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
      crossorigin="anonymous"
    />    <title>Chat</title>
</head>
<body>
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
          <li class="nav-item" active>
            <a class="nav-link" href="Chat.php">Messages<span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="about.php">About</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="contact.php">Contact</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <?php 
   $data = $db->retrieve("Messages");
   $data = json_decode($data, 1);
   if(is_array($data)){
      foreach($data as $id => $email){
         echo "<div class='container' style='width:500px;margin-top:20px'>
         <div class='card'>
           <div class='card-body'>
             <h5 class='card-title' style='text-style:bold;font-family:'poppins';'>Message Subject: {$email['title']}</h5>
             <p class='card-text'>{$email['message']}</p>
             <a href='chat_delete.php' class='card-link' style='color:red;'>Delete</a>
           </div>
         </div>
       </div>";
      }
   }
  ?>

</body>
</html>