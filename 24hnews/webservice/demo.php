<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$a = $_GET['a'];
$b = $_GET['b'];

$r = $a + $b;

$data = array();

$data['kq'] = $r;

header('Content-Type: application/json');
echo json_encode($data);
