<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("DELETE FROM Services WHERE idClient=:id");
	$q->bindParm(':id', $_REQUEST['id']);
	$q->execute();
	
	$q = mysql_query("DELETE FROM Clients WHERE id=:id");
	$q->bindParm(':id', $_REQUEST['id']);
	$q->execute();
	
	$q = mysql_query("DELETE FROM Address WHERE id=:id");
	$q->bindParm(':id', $_REQUEST['id']);
	$q->execute();
?>