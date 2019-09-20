<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"><xsl:template match="/">
  <html>
  <body>
    <h2>BSU</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>ID</th>
        <th>Name</th>
		<th>Faculty</th>
        <th>Year</th>
		<th>Mean Grade</th>
      </tr>
      <xsl:for-each select="bsu/student">
      <tr>
		<td><xsl:value-of select="@id"/></td>
		<td><xsl:value-of select="@name"/></td>
        <td><xsl:value-of select="faculty"/></td>
        <td><xsl:value-of select="year"/></td>
		<td><xsl:value-of select="grade"/></td>
      </tr>
      </xsl:for-each>
    </table>
	<tr>
        <td align="center"><strong>The mean mark of all students: </strong></td>
    </tr>
    <tr>
        <td><xsl:value-of select="sum(/bsu/student/grade) div count(/bsu/student)"/></td>
    </tr>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>