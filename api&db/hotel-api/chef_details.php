<?php
	include("conn.php");

	$chefEmail = $_GET['chefEmail'];
	$chefPasswd = md5($_GET['chefPasswd']);
	
	$q = "select * from chef_details where email='".$chefEmail."' and password='".$chefPasswd."'";

	$result=mysqli_query($con,$q);
	$row = mysqli_num_rows($result);

	if ($row > 0){
		$row = mysqli_fetch_assoc($result);
		echo json_encode($row['id']);
	}
	else
		echo json_encode("error");
?>