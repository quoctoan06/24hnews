<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<?php 
session_start();
include '../libs/func.php'; 

    if(empty($_SESSION['username'])) {
        echo '<center>Bạn chưa đăng nhập, yêu cầu đăng nhập!!!</center>';
        header('Refresh:1; url=login.php'); //delay 1s and go back to login page
        return;
    }
    
    $currentPage = 0;
    
    if(!empty($_GET['page'])) {
        $currentPage = $_GET['page'];
    }
    
    $posts = getPosts($currentPage);
    $totalPage = getTotalPage();
?>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang chủ</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="js/kickstart.js"></script> <!-- KICKSTART -->
        <link rel="stylesheet" href="css/kickstart.css" media="all" /> <!-- KICKSTART -->
    </head>
    <body>
        <div>
            <div>
                <a class="button blue small" href="detail.php"><i class="fa fa-plus"></i>New post</a>
                <a style="float: right" class="button red small" href="logout.php">Logout</a>
            </div>
            <br>
            
            <table class="sortable">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <?php if(!empty($posts)): ?>
                        <?php for($i = 0; $i < count($posts); ++$i): ?>
                            <tr>
                                <td><?= $posts[$i]['post_id'] ?></td>
                                <td><?= $posts[$i]['post_title'] ?></td>
                                <td>
                                    <a href="delete.php?id=<?= $posts[$i]['post_id'] ?>&page=<?= $currentPage ?>" onclick="return confirm('Are you sure ???');" class="button red small">Delete</a>
                                    <a href="edit.php?id=<?= $posts[$i]['post_id'] ?>" class="button blue small">Edit</a>
                                </td>
                            </tr>
                        <?php endfor; ?>
                    <?php else: ?>  
                            <tr>
                                <td class="center" colspan="3">Không có dữ liệu !!!</td>
                            </tr> 
                    <?php endif; ?>
                </tbody>
            </table>
            
            <form method="get" action="">
                Trang: <input style="width: 45px;" type="number" id="page" name="page" value="<?= $currentPage ?>"> / <?= $totalPage ?>
                <button class="blue small" type="submit">Go</button>
            </form>
        </div>
    </body>
</html>
