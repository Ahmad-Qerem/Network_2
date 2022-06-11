<?php

if(isset($_REQUEST["imge"]) && isset($_REQUEST["Name"])) {
    echo $_REQUEST["imge"];
    file_put_contents("./Images/".$_REQUEST["Name"], base64_decode($_REQUEST["imge"]));
}