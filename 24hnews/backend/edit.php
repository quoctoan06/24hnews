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
    
    $post_id = 0;
    
    if(!empty($_GET['id'])) {
        $post_id = $_GET['id'];     //get post id passed from url address
    }
    
    $post = getPostById($post_id);  //get data from database to show 
    
    $post_title = $post['post_title'];
    $post_desc = $post['post_desc'];
    $post_thumb = $post['post_thumb'];
    $category_id = $post['category_id'];
    $post_content = $post['post_content'];
        
    if(!empty($_POST)) {
        $post_title = $_POST['post_title'];
        $post_desc = $_POST['post_desc'];
        $post_thumb = $_POST['post_thumb'];
        $category_id = $_POST['category_id'];
        $post_content = $_POST['post_content'];
    

        $isOK = true;   
        $errors = array();

        if(empty($post_title)) {
            $errors['post_title'] = "Tiêu đề không được bỏ trống";
            $isOK = false;
        }

        if(empty($post_desc)) {
            $errors['post_desc'] = "Mô tả không được bỏ trống";
            $isOK = false;
        }

        if(empty($post_thumb)) {
            $errors['post_thumb'] = "Hình ảnh không được bỏ trống";
            $isOK = false;
        }

        if(empty($post_content)) {
            $errors['post_content'] = "Nội dung không được bỏ trống";
            $isOK = false;
        }

        if(!empty($post_content) && strlen($post_content) < 200) {
            $errors['post_content'] = "Nội dung quá ngắn";
            $isOK = false;
        }

        $state = false;
        if($isOK) {
            //update database
            $state = updatePost($post_id, $post_title, $post_desc, $post_thumb, $post_content, $category_id);
        }
    }
?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông tin chi tiết</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="js/kickstart.js"></script> <!-- KICKSTART -->
        <link rel="stylesheet" href="css/kickstart.css" media="all" /> <!-- KICKSTART -->
        <script src="js/ckeditor/ckeditor.js"></script> <!-- CKEDITOR -->
    </head>
    <body>
        <form style="width: 50%; margin: auto;" class="vertical" method="post" action="">
            <fieldset>
                <legend>Bài viết</legend>
                
                <a href="index.php" class="button blue small">Trang chủ</a>
                
                <div class="notice success <?php if($state) {echo 'show';} else {echo 'hide';} ?>">Sửa bài viết thành công !!!</div>
                
                <label for="post_title">Tiêu đề
                    <span class="right"><?php if(!empty($errors['post_title'])) { echo $errors['post_title']; } ?></span></label>
                <input type="text" name="post_title" id="post_title" 
                       class="<?php if(!empty($errors['post_title'])) { echo 'error'; } ?>" value="<?php if(!empty($post_title)) { echo $post_title; } ?>">
                
                <label for="post_desc" class="<?php if(!empty($errors['post_desc'])) { echo 'error'; } ?>">Mô tả
                    <span class="right"><?php if(!empty($errors['post_desc'])) { echo $errors['post_desc']; } ?></span></label>
                <textarea name="post_desc" id="post_desc"><?php if(!empty($post_desc)) { echo $post_desc; } ?></textarea>
                
                <label for="post_thumb">Hình ảnh
                    <span class="right"><?php if(!empty($errors['post_thumb'])) { echo $errors['post_thumb']; } ?></span></label>
                <input type="text" name="post_thumb" id="post_thumb" 
                       class="<?php if(!empty($errors['post_thumb'])) { echo 'error'; } ?>" value="<?php if(!empty($post_thumb)) { echo $post_thumb; } ?>">
                
                <label for="category_id">Chuyên mục</label>
                <select name="category_id" id="category_id">
                    <option <?php if(empty($category_id) || $category_id == 1) {echo "selected";} ?> value="1">Thời sự</option>
                    <option <?php if(!empty($category_id) && $category_id == 2) {echo "selected";} ?> value="2">Thể thao</option>
                    <option <?php if(!empty($category_id) && $category_id == 3) {echo "selected";} ?> value="3">Kinh tế</option>
                    <option <?php if(!empty($category_id) && $category_id == 4) {echo "selected";} ?> value="4">Chính trị</option>
                </select>
                
                <label for="post_content" class="<?php if(!empty($errors['post_content'])) { echo 'error'; } ?>">Nội dung
                    <span class="right"><?php if(!empty($errors['post_content'])) { echo $errors['post_content']; } ?></span></label>
                <textarea name="post_content" id="post_content"><?php if(!empty($post_content)) { echo $post_content; } ?></textarea>
                
                <script>
                    CKEDITOR.replace(post_content);
                </script>
                
                <button class="blue small" type="submit">Lưu</button>
                
            </fieldset>
        </form>
    </body>
</html>
