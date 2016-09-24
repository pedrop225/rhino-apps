<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	UPDATE Login 
						SET password = :newpass WHERE user = :user");
	
	$q->bindParam(':newpass', $_REQUEST['newpass']);
	$q->bindParam(':user', $_REQUEST['user']);
	
	$q->execute();
?>