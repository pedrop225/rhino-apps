<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("TRUNCATE TABLE Campaigns");
	$q->execute();
	
	$q = $db->prepare("TRUNCATE TABLE CampInfo");
	$q->execute();
?>