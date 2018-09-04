<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

include '../libs/Medoo.php';

// Using Medoo namespace
use Medoo\Medoo;

// Initialize
$database = new Medoo([
    'database_type' => 'mysql',
    'database_name' => '24hnews',
    'server' => 'localhost',
    'port' => '3307',
    'username' => 'root',
    'password' => '1234',
    'charset' => 'utf8'
]);

function getPosts($page = 0) {
    global $database;   //global variable
    
    $totalPostInPage = 10;
    
    $posts = $database->select('post', '*', [
        'ORDER' =>  ['post_id' => 'DESC'],  //show newest posts first
        'LIMIT' => [$page * $totalPostInPage, $totalPostInPage]    //startPosition, quantityOfPost
    ]);
    
    return $posts;
}

function getTotalPage() {
    global $database;
    
    $totalPostInPage = 10;
    
    $totalPost = $database->count('post');
    
    $totalPage = $totalPost / $totalPostInPage;
    
    return floor($totalPage);
}

function deletePost($post_id) {
    global $database;
    
    $state = $database->delete('post', [
        'post_id' => $post_id
    ]);
    
    return $state;  //true or false
}

function insertPost($post_title, $post_desc, $post_thumb, $post_content, $category_id) {
    global $database;
    
    $state = $database->insert('post', [
        'post_title' => $post_title,
        'post_desc' => $post_desc,
        'post_thumb' => $post_thumb,
        'post_content' => $post_content,
        'category_id' => $category_id
    ]);
    
    return $state; //true or false
}

function getPostById($post_id) {
    global $database;
    
    $post = $database->get('post', '*', [
        'post_id' => $post_id
    ]);
    
    return $post;
}

function updatePost($post_id, $post_title, $post_desc, $post_thumb, $post_content, $category_id) {
    global $database;
    
    $state = $database->update('post', [
        'post_title' => $post_title,
        'post_desc' => $post_desc,
        'post_thumb' => $post_thumb,
        'post_content' => $post_content,
        'category_id' => $category_id
    ], [
        'post_id' => $post_id
    ]);
    
    return $state; //true or false
}

function getPostsByCategoryId($category_id, $offset, $limit) {
    global $database;
    
    $posts = $database->select('post', [
        'post_id', 'post_title', 'post_desc', 'post_thumb', 'category_id'
    ], [
        'category_id' => $category_id,
        'ORDER' =>  ['post_id' => 'DESC'],  //show newest posts first
        'LIMIT' => [$offset, $limit]    //startPosition, quantityOfPost
    ]);
    
    return $posts;
}