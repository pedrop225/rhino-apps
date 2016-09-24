<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	DELETE FROM Permissions 
						WHERE idUser= :idUser AND campaign = :campaign");
	
	$q->bindParam(':idUser', $_REQUEST['idUser']);
	$q->bindParam(':campaign', $_REQUEST['campaign']);
	$q->execute();
?>