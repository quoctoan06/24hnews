<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
include '../libs/func.php'; 

$post_id = 0;
$page = 0;

if(!empty($_GET['id'])) {
    $post_id = $_GET['id'];
}

if(!empty($_GET['page'])) {
    $page = $_GET['page'];
}

$state = deletePost($post_id);

if($state) {
    echo '<center>Xoá bài viết thành công !!!</center>';
}
else {
    echo '<center>Xoá bài viết không thành công !!!</center>';
}

header('Refresh:1; url=index.php?page='. $page);
