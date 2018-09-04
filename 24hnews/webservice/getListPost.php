<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
include '../libs/func.php';

$category_id = (!empty($_GET['category_id']))?$_GET['category_id']:1;
$limit = (!empty($_GET['limit']))?$_GET['limit']:3;
$offset = (!empty($_GET['offset']))?$_GET['offset']:0;

$posts = getPostsByCategoryId($category_id, $offset, $limit);

header('Content-Type: application/json');
echo json_encode($posts);