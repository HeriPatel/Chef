<?php
	include("conn.php");

	$tabName = $_GET['tabName'];
	$q = "select id from tab_status where name = '".$tabName."'";
	$r = mysqli_query($con,$q);
	$row = mysqli_fetch_assoc($r);
	$tabID = $row['id'];

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

	$q1 = "select * from ordereddetails where table_id = ".$tabID;
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

	/*
	class response
	{
		public $name;
		public $price;
		public $des;
		public $img;
		public $qnty;
	}
	
	$q2 = 'select id from sub_category where name = "'.$subCatName.'"';
	$result2 = mysqli_query($con,$q2);
	$row2 = mysqli_fetch_assoc($result2);

	$q = "select * from items where sub_cat_id=".$row2['id'];
	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;	
	$row = mysqli_num_rows($result);	
	  while($row = mysqli_fetch_assoc($result)) {
		 $r = new response();
		 $r->name = $row['name'];
		 $r->price = $row['price'];
		 $r->des = $row['description'];
		 $r->img = $row['img'];
		 $r->qnty = $row['qnty'];
		 $arr[$i] = $r;
		 $i = $i + 1;
	  }
	  echo json_encode($arr);	
	*/
?>