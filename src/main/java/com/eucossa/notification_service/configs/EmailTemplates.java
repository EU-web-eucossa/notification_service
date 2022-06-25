package com.eucossa.notification_service.configs;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public class EmailTemplates {

    public static String HTML_BEGIN_TAGS = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Title</title>\n" +
            "   <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor\" crossorigin=\"anonymous\">" +
            "</head>\n" +
            "<body>";
    public static String HTML_END_TAGS = "</body>\n" +
            "</html>";
    public static String ORDER_CONFIRMATION_HEADER = "<div style=\"height: 100px; width: 100%; background-image: linear-gradient(to right, #FC9F5B, #FBD1A2, #ECE4B7, #7DCFB6,#33CA7F); color: black; display: flex; align-content: center; align-items: center\" class=\"container\">\n" +
            "    <h1 style=\"text-align: center; width: 100%; \">Your Order has been Confirmed!</h1>\n" +
            "</div>\n";
    public static String ORDER_CONFIRMATION_BODY = "<div style=\"display: flex; flex-direction: column; align-content: center; align-items: center; width: 100%\">\n" +
            "    <table style=\"margin: auto; width: 400px\">\n" +
            "        <tr style=\"border-bottom: 2px solid darkorange\">\n" +
            "            <th>Image</th>\n" +
            "            <th>Name</th>\n" +
            "            <th>Qnt</th>\n" +
            "            <th>total</th>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td><img style=\"widtd: 100px; height: 100px\" src=\"https://images.unsplash.com/photo-1505740420928-5e560c06d30e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cHJvZHVjdHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\"/></td>\n" +
            "            <td>Amazon Headphones</td>\n" +
            "            <td style=\"border-left: 2px solid red; width: 50px; text-align: center\">2</td>\n" +
            "            <td style=\"font-weight: bolder\">1500</td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td><img style=\"widtd: 100px; height: 100px\" src=\"https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHByb2R1Y3R8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60\"/></td>\n" +
            "            <td>Sneakers</td>\n" +
            "            <td style=\"border-left: 2px solid grey; width: 50px; text-align: center\">2</td>\n" +
            "            <td style=\"font-weight: bolder\">3500</td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td><img style=\"widtd: 100px; height: 100px\" src=\"https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTh8fHByb2R1Y3R8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60\"/></td>\n" +
            "            <td>Sneakers</td>\n" +
            "            <td style=\"border-left: 2px solid grey; width: 50px; text-align: center\">2</td>\n" +
            "            <td style=\"font-weight: bolder\">3500</td>\n" +
            "        </tr>\n" +
            "    </table>\n" +
            "\n" +
            "    <table style=\"border: 2px solid grey; width: 400px; margin: auto\">\n" +
            "        <tr>\n" +
            "            <th>Coupons</th>\n" +
            "            <th>Shippment Fee</th>\n" +
            "            <th>Subtotal</th>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td>Ksh 0</td>\n" +
            "            <td>Ksh 20</td>\n" +
            "            <td><h1>Ksh. 5000</h1></td>\n" +
            "        </tr>\n" +
            "    </table>\n" +
            "</div>";
    public static String ORDER_CONFIRMATION_FOOTER = "";
}
