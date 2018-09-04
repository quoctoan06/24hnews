<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
include '../libs/func.php';

$post_id = (!empty($_GET['post_id']))?$_GET['post_id']:0;

$post = getPostById($post_id);

header('Content-Type: application/json');
echo json_encode($post);
