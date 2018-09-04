<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
session_start();
session_unset();
session_destroy();

echo '<center>Đang chuyển tới trang đăng nhập...</center>';
header('Refresh:1; url=login.php');
