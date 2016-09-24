<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	UPDATE Services
						SET state = :state, notes = :notes,
							referencia = :referencia, f_pago = :f_pago,
							p_neta = :p_neta, ccc = :ccc,
							cartera = :cartera, anualizar = :anualizar
							WHERE (id = :id)");
	
	$q->bindParam(':id', $_REQUEST['id']);
	$q->bindParam(':state', $_REQUEST['state']);
	$q->bindParam(':notes', $_REQUEST['notes']);
	$q->bindParam(':referencia', $_REQUEST['referencia']);
	$q->bindParam(':f_pago', $_REQUEST['f_pago']);
	$q->bindParam(':p_neta', $_REQUEST['p_neta']);
	$q->bindParam(':ccc', $_REQUEST['ccc']);
	$q->bindParam(':cartera', $_REQUEST['cartera']);
	$q->bindParam(':anualizar', $_REQUEST['anualizar']);
	
	$q->execute();
?>