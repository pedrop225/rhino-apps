<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	SELECT Services.id, idClient, name, campaign, service, date, expiry, commission, state, notes,
								referencia, f_pago, p_neta, ccc, cartera, anualizar
						FROM Clients, Services
						WHERE 	(idUser = :idUser) AND
								(Clients.id = Services.idClient) AND
								((state = 1 AND cartera = 1) OR (YEAR(date) = YEAR(NOW()))) AND
								(MONTH(date) = MONTH(DATE_ADD(NOW(), INTERVAL 7 DAY)))");
	
	$q->bindParam(':idUser', $_REQUEST['idUser']);
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;
	
	print(json_encode($output));
?>