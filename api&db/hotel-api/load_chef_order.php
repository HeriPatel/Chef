<?php
	include("conn.php");

	$id = $_REQUEST['id'];

	class order
	{
		public $id;
		public $qnty;
		public $name;
		public $des;
		public $img;
		public $price;
		public $itemNotes;
	}

	$q1 = "select * from ordereddetails where chef_id = ".$id;
	$r1 = mysqli_query($con,$q1);
	$row1 = mysqli_num_rows($r1);

	$orderArr = array();
	$i = 0;

	while ($row1 = mysqli_fetch_assoc($r1)) {
		$obj = new order();
		$itemID = $row1['item_id'];
		$q2 = "select * from items where id = ".$itemID;
		$r2 = mysqli_query($con,$q2);
		$row2 = mysqli_fetch_assoc($r2);
		$obj->id = $row1['id'];
		$obj->name = $row2['name'];
		$obj->des = $row2['description'];
		$obj->img = $row2['img'];
		$obj->price = $row2['price'];
		$obj->qnty = $row1['item_qnty'];
		$obj->itemNotes = $row1['itemNotes'];
		$orderArr[$i] = $obj;
		$i++;
	}
	echo json_encode($orderArr);
?>