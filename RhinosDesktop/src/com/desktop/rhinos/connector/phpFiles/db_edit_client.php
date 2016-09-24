<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	UPDATE Clients 
								SET name = :name, b_date = :b_date,
								tlf_1 = :tlf_1, tlf_2 = :tlf_2,
								mail = :mail, consultancy = :consultancy
								WHERE id = :id");
	
	$q->bindParam(':name', $_REQUEST['name']);
	$q->bindParam(':b_date', $_REQUEST['b_date']);
	$q->bindParam(':tlf_1', $_REQUEST['tlf_1']);
	$q->bindParam(':tlf_2', $_REQUEST['tlf_2']);
	$q->bindParam(':mail', $_REQUEST['mail']);
	$q->bindParam(':consultancy', $_REQUEST['consultancy']);
	$q->bindParam(':id', $_REQUEST['id']);
	
	$q->execute();
	
	$q = mysql_query("	UPDATE Address
								SET tipo_via = :tipo_via, nombre_via = :nombre_via,
								numero = :numero, portal = :portal,
								escalera = :escalera, piso = :piso,
								puerta = :puerta, poblacion = :poblacion,
								municipio = :municipio, cp = :cp
								WHERE id = :id");	
	
	$q->bindParam(':tipo_via', $_REQUEST['tipo_via']);
	$q->bindParam(':nombre_via', $_REQUEST['nombre_via']);
	$q->bindParam(':numero', $_REQUEST['numero']);
	$q->bindParam(':portal', $_REQUEST['portal']);
	$q->bindParam(':escalera', $_REQUEST['escalera']);
	$q->bindParam(':piso', $_REQUEST['piso']);
	$q->bindParam(':puerta', $_REQUEST['puerta']);
	$q->bindParam(':poblacion', $_REQUEST['poblacion']);
	$q->bindParam(':municipio', $_REQUEST['municipio']);
	$q->bindParam(':cp', $_REQUEST['cp']);
	$q->bindParam(':id', $_REQUEST['id']);
	
	$q->execute();
?>