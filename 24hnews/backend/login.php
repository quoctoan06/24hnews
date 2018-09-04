<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<?php
session_start();
session_unset();    //frees all session variables currently registered

    if(!empty($_POST)) {
        $username = $_POST['username'];
        $password = $_POST['password'];
        
        $error = false;
        
        //there is only one user: admin
        if($username == 'admin' && $password == '123456') {
            $_SESSION['username'] = $username;
            $_SESSION['password'] = $password;
            header('Location: index.php');
        }
        else {
            $error = true;
        }
    }
?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="js/kickstart.js"></script> <!-- KICKSTART -->
        <link rel="stylesheet" href="css/kickstart.css" media="all" /> <!-- KICKSTART -->
    </head>
    <body>
        <form style="width: 30%; margin: auto;" class="vertical" action="" method="post">
            <fieldset>
                <legend>Đăng nhập</legend>
                
                <div class="notice error <?php if($error) {echo 'show';} else {echo 'hide';} ?>">
                    <i class="fa fa-warning"></i>Sai tài khoản hoặc mật khẩu</div> 
                
                <label for="username">Tài khoản</label>
                <input type="text" name="username" id="username">
                
                <label for="password">Mật khẩu</label>
                <input type="password" name="password" id="password">
                
                <button class="blue" type="submit">Đăng nhập</button>
            </fieldset>
        </form>
    </body>
</html>
