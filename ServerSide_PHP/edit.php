<?php
$servername = "localhost";
$username = "root";
$password = "";

// Create connection
$conn = new mysqli($servername, $username, $password);
$db = new mysqli('localhost', 'root', '', 'network2');

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset( $_REQUEST['name'])&& isset( $_REQUEST['Add'])&& isset( $_REQUEST['Email'])
    && isset( $_REQUEST['Pass'])&& isset( $_REQUEST['Id']) && isset( $_REQUEST['img'])) {
    $Fname = $_REQUEST['Fname'];
    $Lname = $_REQUEST['Lname'];

    $Name=  $_REQUEST['name'];
    $Address = $_REQUEST['Add'];
    $Email = $_REQUEST['Email'];
    $Pass = $_REQUEST['Pass'];
    $Id = (int)$_REQUEST['Id'];
    $Photo = $_REQUEST['img'];
    $email_ex = 0;
    $sqll = "SELECT email FROM network2.employee WHERE id != '$Id'";
    $resultt = $conn->query($sqll);
    $sql = "UPDATE network2.employee SET name='$Name',address= '$Address',email= '$Email',password= '$Pass', photo='$Photo' WHERE id = '$Id'";

    if ($resultt->num_rows > 0) {
        while($row = $resultt->fetch_assoc()) {
            if ($row["email"] == $Email){
                $email_ex = 1;
            }
        }
        if ($email_ex == 1) {
            echo "Email is already exist!";
        }
        else {
            $result = $conn->query($sql);
            echo "User Updated successfully";
        }
    }
}
?>