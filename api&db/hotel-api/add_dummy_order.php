<?php
	include("conn.php");

	$id = $_REQUEST['id'];

	$query = "insert into dummy_order_details (`order_id`) values (".$id.")";

	$result = mysqli_query($con,$query);
	if ($result)
		echo json_encode($con->insert_id);
	else
		echo json_encode("error");
?>