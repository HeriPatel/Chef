<?php
	include("conn.php");


	$q1 = "select * from dummy_order_details";
	$r1 = mysqli_query($con,$q1);
	$row1 = mysqli_num_rows($r1);

	if($row1 > 0)
	{
		$row1 = mysqli_fetch_assoc($r1);
		$q2 = "delete from dummy_order_details where id = ".$row1['id'];
		$r2 = mysqli_query($con,$q2);

		if($r2)
			echo json_encode($row1['order_id']);
		else
			echo json_encode("error");
	}
	else
		echo json_encode("error");	
?>