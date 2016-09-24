<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	INSERT INTO Services (idUser, idClient, service, campaign, commission, date, expiry, state) 
						VALUES(:idUser, :idClient, :service, :campaign, :comission, :date, :expiry, :state)");
	
	$q->bindParam(':idUser', $_REQUEST['idUser']);
	$q->bindParam(':idClient', $_REQUEST['idClient']);
	$q->bindParam(':service', $_REQUEST['service']);
	$q->bindParam(':campaign', $_REQUEST['campaign']);
	$q->bindParam(':commission', $_REQUEST['commission']);
	$q->bindParam(':date', $_REQUEST['date']);
	$q->bindParam(':expiry', $_REQUEST['expiry']);
	$q->bindParam(':state', $_REQUEST['state']);
	
	$q->execute();
?>
