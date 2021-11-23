<?php
	include("conn.php");

	$chefEmail = $_GET['chefEmail'];
	$chefName = $_GET['chefName'];
	$chefPhone = $_GET['chefPhone'];
	$chefPasswd = md5($_GET['chefPasswd']);

	$q = "insert into chef_details(`email`, `name`, `password`,`phone_num`) values ('".$chefEmail."','".$chefName."','".$chefPasswd."',".$chefPhone.")";

	$result = mysqli_query($con,$q);

	if ($result){
		$id = $con->insert_id;
		$q1 = "insert into chef_status(`chef_id`,`status`) values (".$id.",'Available')";
		$result1 = mysqli_query($con,$q1);
		if($result1)
			echo json_encode($id);
	}
	else
		echo json_encode("error");
?>