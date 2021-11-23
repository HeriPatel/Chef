<?php	
	include("conn.php");

	$q = "select * from chef_status where status='Available'";

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	$row = mysqli_num_rows($result);

	if($row = mysqli_fetch_assoc($result))
		echo json_encode($row ['id']);
	else
		echo "Error!";
?>