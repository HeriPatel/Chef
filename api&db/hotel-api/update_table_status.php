<?php
	include("conn.php");
	$tabName = $_GET['tabName'];
	$tabStatus = $_GET['tabStatus'];

	$query = "update tab_status set status='".$tabStatus."' where name='".$tabName."'";

	$result = mysqli_query($con,$query);
	if ($result)
		echo json_encode("done");
	else
		echo json_encode("error");
?>