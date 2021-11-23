<?php
	include("conn.php");
	

	$q = "select * from ordereddetails where chef_id=NULL";

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	
	$row = mysqli_num_rows($result);
	
	$row = mysqli_fetch_assoc($result);
	echo json_encode($row['id']);
?>