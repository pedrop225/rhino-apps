<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	UPDATE Consultancy
						SET name = :name, consultant = :consultant,
							tlf_1 = :tlf_1, tlf_2 = :tlf_2,
							mail = :mail
							WHERE id = :id");
	
	$q->bindParam(':id', $_REQUEST['id']);
	$q->bindParam(':name', $_REQUEST['name']);
	$q->bindParam(':consultant', $_REQUEST['consultant']);
	$q->bindParam(':tlf_1', $_REQUEST['tlf_1']);
	$q->bindParam(':tlf_2', $_REQUEST['tlf_2']);
	$q->bindParam(':mail', $_REQUEST['mail']);
	
	$q->execute();
?>