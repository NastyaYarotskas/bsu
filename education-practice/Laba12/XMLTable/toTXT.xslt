<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"><xsl:template match="/">

<xsl:for-each select="bsu/student">
newStudent
    id:<xsl:value-of select="@id"/>
	name:<xsl:value-of select="@name"/>
    faculty:<xsl:value-of select="faculty"/>
    year:<xsl:value-of select="year"/>
	Mean Grade:<xsl:value-of select="grade"/>
</xsl:for-each>
</xsl:template>

</xsl:stylesheet>