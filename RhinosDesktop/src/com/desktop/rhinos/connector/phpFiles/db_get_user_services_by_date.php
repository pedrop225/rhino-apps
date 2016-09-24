<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$date_type = ($_REQUEST['date_type'] == 0) ? 'date' : 'expiry';
	
	$q = $db->prepare("	SELECT Services.id, idClient, name, campaign, service, date, expiry, commission, state, notes,
								referencia, f_pago, p_neta, ccc, cartera, anualizar
						FROM Clients, Services
						WHERE idUser= :idUser AND 
								Clients.id=Services.idClient AND
								$date_type >= :date_in AND 
								$date_type <= :date_out ORDER BY date");

	$q->bindParam(':idUser', $_REQUEST['idUser']);
	$q->bindParam(':date_in', $_REQUEST['date_in']);
	$q->bindParam(':date_out', $_REQUEST['date_out']);
	
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;
	
	print(json_encode($output));
?>