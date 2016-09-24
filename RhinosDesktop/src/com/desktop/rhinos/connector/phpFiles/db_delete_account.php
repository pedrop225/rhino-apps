<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("DELETE FROM Login WHERE id=:id");
	$q->bindParam(':id', $_REQUEST['id']);
	$q->execute();
	
	$q = $db->prepare("DELETE FROM Users WHERE id=:id");
	$q->bindParam(':id', $_REQUEST['id']);
	$q->execute();
	
	$q = $db->prepare("DELETE FROM Structure WHERE child=:id");
	$q->bindParam(':id', $_REQUEST['id']);
	$q->execute();
?>