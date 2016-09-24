<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	INSERT INTO CampInfo (id, service, commission) 
						VALUES (:id, :service, :commission)");
	
	$q->bindParam(':id', $_REQUEST['id']);
	$q->bindParam(':service', $_REQUEST['service']);
	$q->bindParam(':commission', $_REQUEST['commission']);
	$q->execute();
?>