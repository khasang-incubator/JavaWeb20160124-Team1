<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="UTF-8">
    <title>My 7th Home Work</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
<h1> ${dropmenu} </h1>
<center>
    <font class="darkblue">Drop menu</font>
    <br/>
    <select><option>Select</option>
        <option>Table 1</option>
        <option>Table 2</option>
        <option>Table 3</option>
        <input type="submit" value="Select">
</center>
<br/><br/>
<font class="darkblue"><h1> ${table1} </h1></font>
<center><table border="1">
    <caption>Shoe sizes table</caption>
    <tr>
        <th>Russia</th>
        <th>Great Britan</th>
        <th>Europe</th>
        <th>Length of the foot, cm</th>
    </tr>
    <tr><td>34,5</td><td>3,5</td><td>36</td><td>23</td></tr>
    <tr><td>35,5</td><td>4</td><td>36⅔</td><td>23–23,5</td></tr>
    <tr><td>36</td><td>4,5</td><td>37⅓</td><td>23,5</td></tr>
    <tr><td>36,5</td><td>5</td><td>38</td><td>24</td></tr>
    <tr><td>37</td><td>5,5</td><td>38⅔</td><td>24,5</td></tr>
    <tr><td>38</td><td>6</td><td>39⅓</td><td>25</td></tr>
    <tr><td>38,5</td><td>6,5</td><td>40</td><td>25,5</td></tr>
    <tr><td>39</td><td>7</td><td>40⅔</td><td>25,5–26</td></tr>
    <tr><td>40</td><td>7,5</td><td>41⅓</td><td>26</td></tr>
    <tr><td>40,5</td><td>8</td><td>42</td><td>26,5</td></tr>
    <tr><td>41</td><td>8,5</td><td>42⅔</td><td>27</td></tr>
    <tr><td>42</td><td>9</td><td>43⅓</td><td>27,5</td></tr>
    <tr><td>43</td><td>9,5</td><td>44</td><td>28</td></tr>
    <tr><td>43,5</td><td>10</td><td>44⅔</td><td>28–28,5</td></tr>
    <tr><td>44</td><td>10,5</td><td>45⅓</td><td>28,5–29</td></tr>
    <tr><td>44,5</td><td>11</td><td>46</td><td>29</td></tr>
    <tr><td>45</td><td>11,5</td><td>46⅔</td><td>29,5</td></tr>
    <tr><td>46</td><td>12</td><td>47⅓</td><td>30</td></tr>
    <tr><td>46,5</td><td>12,5</td><td>48</td><td>30,5</td></tr>
    <tr><td>47</td><td>13</td><td>48⅔</td><td>31</td></tr>
    <tr><td>48</td><td>13,5</td><td>49⅓</td><td>31,5</td></tr>
</table></center>
<br/><br/>
<center>
    <p style="text-align: center"><button>Back to menu</button> <br/><br/>
        <button><img src="http://3674fbead0c29942205b633c.nectivityltd.netdna-cdn.com/wp-content/uploads/2011/03/waves-50x50.jpg" style="vertical-align: middle">Pic button (50 x 50 px)</button>
        <br/><br/>
        <button>Logoff</button>
    </p>
</center>
</body>
</html>