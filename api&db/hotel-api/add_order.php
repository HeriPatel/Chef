<?php
	include("conn.php");

	$itemName = $_GET['itemName'];
	$tableName = $_GET['tableName'];
	$itemQnty = $_GET['itemQnty'];
	$itemNotes = $_GET['itemNotes'];

	$q1 = "select id from items where name = '".$itemName."'";
	$r1 = mysqli_query($con,$q1);
	$row1 = mysqli_fetch_assoc($r1);
	$itemID = $row1['id'];

	$q2 = "select id from tab_status where name = '".$tableName."'";
	$r2 = mysqli_query($con,$q2);
	$row2 = mysqli_fetch_assoc($r2);
	$tableID = $row2['id'];

	$query = "insert into ordereddetails (`table_id`, `item_id`, `item_qnty`,`itemNotes`) values (".$tableID.",".$itemID.",".$itemQnty.",'".$itemNotes."')";

	$result = mysqli_query($con,$query);
	if ($result)
		echo json_encode($con->insert_id);
	else
		echo json_encode("error");
?>