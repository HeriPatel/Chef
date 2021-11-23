<?php
	$con = mysqli_connect("localhost","root","","hotel");
	if (!$con) {
		echo json_encode("Error");
	}
?>