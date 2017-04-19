<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	INSERT INTO Documents (idService, name, date, doc)
							VALUES(:idService, :name, :date, :doc)");
	
	$q->bindParam(':idService', $_REQUEST['idService']);
	$q->bindParam(':name', $_REQUEST['name']);
	$q->bindParam(':date', $_REQUEST['date']);
	$q->bindParam(':doc', $_REQUEST['doc']);
	
	$q->execute();	
?>