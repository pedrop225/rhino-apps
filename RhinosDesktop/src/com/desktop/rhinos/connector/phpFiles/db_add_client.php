<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare(" INSERT INTO Clients (id, b_date, name, tlf_1, tlf_2, mail, consultancy) 
						VALUES(:id, :b_date, :name, :tlf_1, :tlf_2, :mail, :consultancy)");
	
	$q->bindParam(':id', $_REQUEST['id']);
	$q->bindParam(':b_date', $_REQUEST['b_date']);
	$q->bindParam(':name', $_REQUEST['name']);
	$q->bindParam(':tlf_1', $_REQUEST['tlf_1']);
	$q->bindParam(':tlf_2', $_REQUEST['tlf_2']);
	$q->bindParam(':mail', $_REQUEST['mail']);
	$q->bindParam(':consultancy', $_REQUEST['consultancy']);
	
	$q->execute();
	
	$q = $db->prepare("	INSERT INTO Address (id, tipo_via, nombre_via, numero, portal, 
											escalera, piso, puerta, poblacion, municipio, cp) 
						VALUES(:id, :tipo_via, :nombre_via, :numero, :portal,
							 	:escalera, :piso, :puerta, :poblacion, :municipio, :cp)");
	
	$q->bindParam(':id', $_REQUEST['id']);
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
	
	$q->execute();
?>