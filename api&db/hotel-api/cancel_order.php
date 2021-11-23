<?php
	include("conn.php");

	$id = $_GET['id'];
	$query = "delete from ordereddetails where id = ".$id;
	$result = mysqli_query($con,$query);
	if ($result)
		echo json_encode("done");
	else
		echo json_encode("error");
?>