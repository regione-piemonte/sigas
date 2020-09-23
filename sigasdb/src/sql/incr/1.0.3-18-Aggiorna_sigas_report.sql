UPDATE sigas_report
SET jrxml=cast('<!-- Created with Jaspersoft Studio version 6.3.1.final using JasperReports Library version 6.3.1  -->
<!-- 2019-12-18T15:51:18 -->
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="dichiarazione" pageWidth="2000" pageHeight="850" columnWidth="1980" leftMargin="20" rightMargin="0" topMargin="20" bottomMargin="0" whenResourceMissingType="Empty" isIgnorePagination="true" uuid="8b824d48-44af-42b8-864e-1eac359ac32f">
	<property name="com.jaspersoft.studio.data.sql.tables" value=""/>
	<property name="net.sf.jasperreports.export.xls.white.page.background" value="false"/>
	<property name="net.sf.jasperreports.export.xls.detect.cell.type" value="true"/>
	<property name="net.sf.jasperreports.export.xls.ignore.graphics" value="false"/>
	<property name="net.sf.jasperreports.exports.xls.font.size.fix.enabled" value="false"/>
	<property name="net.sf.jasperreports.export.xls.remove.empty.space.between.columns" value="false"/>
	<property name="net.sf.jasperreports.export.xls.remove.empty.space.between.rows" value="false"/>
	<property name="com.jaspersoft.studio.data.defaultdataadapter" value="Sample DB"/>
	<style name="Table_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 1_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 1_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 1_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 2_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 2_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 2_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 3_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 3_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 3_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<subDataset name="dichiarazioneConsumi" uuid="878aa02e-f5d0-46db-95ab-ebf65634f7ea">
		<property name="com.jaspersoft.studio.data.sql.tables" value=""/>
		<property name="com.jaspersoft.studio.data.defaultdataadapter" value="Sample DB"/>
		<parameter name="anno" class="java.lang.String"/>
		<parameter name="id_anag" class="java.lang.Long"/>
		<queryString>
			<![CDATA[select provincia_erogazione, data_presentazione, usi_industriali_1200, usi_industriali_up, tot_industriali,
	usi_civili_120, usi_civili_480, usi_civili_1560, usi_civili_up, tot_civili, tot_nuovi_allacciamenti,
	conguaglio_dich,  
	sdc.conguaglio_calcolato - (select (case when sum(sdv.importo ) is null then 0 else sum(sdv.importo ) end) 
								from sigas_dich_consumi sdcc right join sigas_dich_versamenti sdv 
										on sdv.id_consumi = sdcc.id_consumi  
								where sdcc.annualita = $P{anno}  and sdcc.id_anag = $P{id_anag}  
								and sdc.provincia_erogazione = (select sp.sigla_provincia from sigas_provincia sp where sp.id_provincia = sdv.fk_provincia )  ) conguaglio_calcolato, 
	rettifiche, arrotondamenti, totale_calcolato 
	from sigas_dich_consumi sdc 
	where sdc.annualita =  $P{anno} and sdc.id_anag = $P{id_anag} and sdc.mod_id_consumi = 0 order by sdc.provincia_erogazione]]>
		</queryString>
		<field name="provincia_erogazione" class="java.lang.String">
			<property name="com.jaspersoft.studio.field.label" value="provincia_erogazione"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="data_presentazione" class="java.sql.Timestamp">
			<property name="com.jaspersoft.studio.field.label" value="data_presentazione"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_industriali_1200" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_industriali_1200"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_industriali_up" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_industriali_up"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="tot_industriali" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="tot_industriali"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_120" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_120"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_480" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_480"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_1560" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_1560"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_up" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_up"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="tot_civili" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="tot_civili"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="tot_nuovi_allacciamenti" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="tot_nuovi_allacciamenti"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="conguaglio_dich" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="conguaglio_dich"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="conguaglio_calcolato" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="conguaglio_calcolato"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="rettifiche" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="rettifiche"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="arrotondamenti" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="arrotondamenti"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="totale_calcolato" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="totale_calcolato"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
	</subDataset>
	<parameter name="anno" class="java.lang.String"/>
	<parameter name="id_anag" class="java.lang.Long"/>
	<queryString>
		<![CDATA[select a.denominazione as denominazione, sum(usi_industriali_1200) as sum_ind_1200, sum(usi_industriali_up) as sum_ind_up, sum(tot_industriali) as sum_ind,
	sum(usi_civili_120) as sum_civ_120, sum(usi_civili_480) as sum_civ_480, sum(usi_civili_1560) as sum_civ_1560, sum(usi_civili_up) as sum_civ_up, sum(tot_civili) as sum_civ, sum(tot_nuovi_allacciamenti) as sum_all,
	sum(conguaglio_dich) as sum_cong_dich, sum(conguaglio_calcolato) - (select (case when sum(sdv.importo ) is null then 0 else sum(sdv.importo ) end) 
								from sigas_dich_consumi sdcc right join sigas_dich_versamenti sdv 
										on sdv.id_consumi = sdcc.id_consumi  
								where sdcc.annualita = $P{anno}  and sdcc.id_anag = $P{id_anag}   )  as sum_cong_calc, sum(rettifiche) as sum_rett, sum(arrotondamenti) as sum_arr, sum(totale_calcolato) as sum_tot from sigas_dich_consumi c, sigas_anagrafica_soggetti a 
	where annualita = $P{anno} and c.id_anag = $P{id_anag} and mod_id_consumi = 0 and a.id_anag = $P{id_anag}
	group by a.denominazione]]>
	</queryString>
	<field name="denominazione" class="java.lang.String"/>
	<field name="sum_ind_1200" class="java.lang.Integer"/>
	<field name="sum_ind_up" class="java.lang.Integer"/>
	<field name="sum_ind" class="java.math.BigDecimal"/>
	<field name="sum_civ_120" class="java.lang.Integer"/>
	<field name="sum_civ_480" class="java.lang.Integer"/>
	<field name="sum_civ_1560" class="java.lang.Integer"/>
	<field name="sum_civ_up" class="java.lang.Integer"/>
	<field name="sum_civ" class="java.math.BigDecimal"/>
	<field name="sum_all" class="java.math.BigDecimal"/>
	<field name="sum_cong_dich" class="java.math.BigDecimal"/>
	<field name="sum_cong_calc" class="java.math.BigDecimal"/>
	<field name="sum_rett" class="java.math.BigDecimal"/>
	<field name="sum_arr" class="java.math.BigDecimal"/>
	<field name="sum_tot" class="java.math.BigDecimal"/>
	<variable name="imgPiemonte" class="java.lang.String">
		<variableExpression><![CDATA["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"]]></variableExpression>
	</variable>
	<columnHeader>
		<band height="280">
			<frame>
				<reportElement x="0" y="0" width="960" height="110" uuid="a5dc679a-1d12-4ebf-8584-785e75d6ff24"/>
				<image evaluationTime="Report">
					<reportElement x="0" y="0" width="360" height="60" uuid="35603c9e-801b-4bb5-8b11-5c2004529667"/>
					<imageExpression><![CDATA[javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer($V{imgPiemonte})))]]></imageExpression>
				</image>
				<staticText>
					<reportElement x="0" y="60" width="360" height="15" uuid="bcc2eb00-281b-47be-9adb-6cd0068b6b65"/>
					<text><![CDATA[Direzione Risorse Finanziarie e Patrimonio]]></text>
				</staticText>
				<staticText>
					<reportElement x="0" y="75" width="360" height="15" uuid="48cec07b-685c-4ecf-934e-2c25bac72e72">
						<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
					</reportElement>
					<text><![CDATA[Settore Politiche Fiscali e Contenzioso amministrativo]]></text>
				</staticText>
				<staticText>
					<reportElement x="0" y="90" width="360" height="20" uuid="ce4d62ee-aa2f-494a-9834-e0d4e7142d4e"/>
					<text><![CDATA[settore.tributi@regione.piemonte.it]]></text>
				</staticText>
			</frame>
			<staticText>
				<reportElement x="0" y="130" width="1980" height="30" uuid="84c5a6e2-172f-45aa-8ed1-b15b80ea7275"/>
				<textElement textAlignment="Center" verticalAlignment="Middle">
					<font size="22"/>
				</textElement>
				<text><![CDATA[Sistema informativo Gas Naturale]]></text>
			</staticText>
			<staticText>
				<reportElement x="0" y="187" width="1980" height="30" uuid="934eb491-17bc-4ef5-94c4-1a160dad06cd"/>
				<textElement textAlignment="Center" verticalAlignment="Middle">
					<font size="20"/>
				</textElement>
				<text><![CDATA[Dettagli dichiarazione consumi]]></text>
			</staticText>
		</band>
	</columnHeader>
	<detail>
		<band height="95" splitType="Stretch">
			<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
			<textField>
				<reportElement x="0" y="35" width="1980" height="30" isRemoveLineWhenBlank="true" uuid="ed12b3eb-3b1f-45c1-aa26-c0a425bb0308">
					<printWhenExpression><![CDATA[$P{anno} != null]]></printWhenExpression>
				</reportElement>
				<textElement textAlignment="Left" verticalAlignment="Middle">
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["Soggetto dichiarante:  " + $F{denominazione}]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="0" y="1" width="240" height="30" isRemoveLineWhenBlank="true" uuid="120c2b25-5a21-4170-ad94-2739f07e1dbb">
					<printWhenExpression><![CDATA[$P{anno} != null]]></printWhenExpression>
				</reportElement>
				<textElement textAlignment="Left" verticalAlignment="Middle">
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["Anno Dichiarazione:  " + $P{anno}]]></textFieldExpression>
			</textField>
		</band>
		<band height="123">
			<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
			<componentElement>
				<reportElement key="" isPrintRepeatedValues="false" x="0" y="-1" width="1920" height="61" uuid="e15d6a63-1968-413d-91b5-53b9c4298d16">
					<property name="com.jaspersoft.studio.layout" value="com.jaspersoft.studio.editor.layout.VerticalRowLayout"/>
					<property name="com.jaspersoft.studio.table.style.table_header" value="Table 3_TH"/>
					<property name="com.jaspersoft.studio.table.style.column_header" value="Table 3_CH"/>
					<property name="com.jaspersoft.studio.table.style.detail" value="Table 3_TD"/>
					<property name="net.sf.jasperreports.export.headertoolbar.table.name" value="dichiarazioni"/>
					<property name="com.jaspersoft.studio.unit.y" value="pixel"/>
				</reportElement>
				<jr:table xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd" whenNoDataType="AllSectionsNoDetail">
					<datasetRun subDataset="dichiarazioneConsumi" uuid="2bf38382-8ac0-4f92-975a-7f5e90ec1e48">
						<datasetParameter name="anno">
							<datasetParameterExpression><![CDATA[$P{anno}]]></datasetParameterExpression>
						</datasetParameter>
						<datasetParameter name="id_anag">
							<datasetParameterExpression><![CDATA[$P{id_anag}]]></datasetParameterExpression>
						</datasetParameter>
						<connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>
					</datasetRun>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna1"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Provincia]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{provincia_erogazione}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="49746f88-33e7-40a7-9c8d-69a9e0fb6038">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna2"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="0a8f65e8-58ce-491a-8b1a-e2f012bdfab5">
									<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
								</reportElement>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Data]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="dd/MM/yyyy" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f6c4633e-53cf-4f53-8ef1-6813d4239313"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{data_presentazione}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="9ae99573-011d-45cc-82fa-0691ab4bb66e">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna3"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="a2c5feef-315a-4fbc-b943-1f1f3ffab59b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Industriale 1200]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="1046e737-fcc3-4c57-9f5c-fddaa2d8415e"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_industriali_1200}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="23f9cd33-5a38-4507-baa3-78de414adbc2">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna4"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="5345aca6-d422-4ec5-9031-68b838db858b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Industriale > 1200]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="9bb5b062-4c79-4b31-9441-b2e21b03f08f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_industriali_up}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="f30c151f-33ea-4f93-be7c-c485265b3bbe">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna5"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="5f048843-38a7-4d59-939c-1c80a6874995"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale Industriale]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="2a85df3a-bbbb-457e-b775-80300545c01c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{tot_industriali}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="0151c8bd-0147-4319-8834-1cde34566c22">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna6"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="0f3103b9-c477-41d5-9a52-56b78f05c2ed"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile 120]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f64a77e6-9efa-49e8-853c-6e9ba5a16e9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_120}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="c73c8084-d6c8-4199-a52b-ca65b55ecc82">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna7"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="7108b3b8-1fbe-4c14-a7a6-1f241e2f0d93">
									<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
								</reportElement>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile 480]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f6c870db-8b12-4845-82ff-a0d2b5554693"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_480}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="b72e02cd-face-49f2-813d-fe2785864c9e">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna8"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<staticText>
								<reportElement positionType="Float" x="0" y="0" width="120" height="30" uuid="35829659-8dc9-4131-9327-d5b3de8214e8"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile 1560]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="65a6b417-8280-430b-9d2d-b447b8b74956"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_1560}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna9"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile > 1560]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_up}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna10"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale Civile]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{tot_civili}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna11"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale nuovi allac.]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{tot_nuovi_allacciamenti}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna12"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Conguaglio dich.]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{conguaglio_dich}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna13"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Conguaglio calc.]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{conguaglio_calcolato}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna14"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Rettifiche]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{rettifiche}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna15"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Arrotondamenti]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{arrotondamenti}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna16"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{totale_calcolato}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
				</jr:table>
			</componentElement>
			<textField pattern="#,##0">
				<reportElement positionType="Float" x="240" y="60" width="120" height="20" uuid="66b1e0bc-7a71-4c09-8317-ec898ab911d3"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_ind_1200}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement positionType="Float" x="360" y="60" width="120" height="20" uuid="2b4de619-be5d-42ba-a725-a81f4fae0b07"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_ind_up}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="480" y="60" width="120" height="20" uuid="b4980843-2a0b-475b-891a-3a4284640ea6"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_ind}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement positionType="Float" x="600" y="60" width="120" height="20" uuid="a081048a-6746-49b4-8885-f77755bc8251"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_120}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement positionType="Float" x="720" y="60" width="120" height="20" uuid="13d85505-9199-4e21-87a7-218b9e51af69"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_480}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement positionType="Float" x="840" y="60" width="120" height="20" uuid="97eb99e2-dd3c-405f-aa0c-72cccbf9cdce">
					<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
				</reportElement>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_1560}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement positionType="Float" x="960" y="60" width="120" height="20" uuid="64ee9a4b-8146-4c3d-99cf-53c420224b65"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_up}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1080" y="60" width="120" height="20" uuid="467d1b70-3e31-459c-8df9-0e4501c094ab"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1200" y="60" width="120" height="20" uuid="9e855fcc-9683-421b-92a3-0e460849efe6"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_all}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1320" y="60" width="120" height="20" uuid="73ed4801-c107-4064-8dec-2a916e41ed6f"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_cong_dich}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1440" y="60" width="120" height="20" uuid="497d4c9e-1403-49ea-a289-e90157b6ad32"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_cong_calc}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1560" y="60" width="120" height="20" uuid="a9a32e68-dc6a-4c89-9f3f-4bb3b58571f0"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_rett}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1680" y="60" width="120" height="20" uuid="a3b5d25e-588d-4e12-9c84-627638e6f684"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_arr}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement positionType="Float" x="1800" y="60" width="120" height="20" uuid="6ebfc9e9-53ad-4d96-8775-2aef6c3ce6ef"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_tot}]]></textFieldExpression>
			</textField>
		</band>
	</detail>
	<columnFooter>
		<band height="45">
			<property name="com.jaspersoft.studio.unit.height" value="px"/>
		</band>
	</columnFooter>
</jasperReport>'as xml), jasper=NULL
WHERE id_report=1;
UPDATE sigas_report
SET jrxml=cast('<!-- Created with Jaspersoft Studio version 6.10.0.final utlizzo versione della libreria JasperReports6.10.0-unknown  -->
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="dichiarazione" pageWidth="2000" pageHeight="850" columnWidth="1980" leftMargin="20" rightMargin="0" topMargin="20" bottomMargin="0" whenResourceMissingType="Empty" isIgnorePagination="true" uuid="8b824d48-44af-42b8-864e-1eac359ac32f">
	<property name="com.jaspersoft.studio.data.sql.tables" value=""/>
	<property name="net.sf.jasperreports.export.xls.white.page.background" value="false"/>
	<property name="net.sf.jasperreports.export.xls.detect.cell.type" value="true"/>
	<property name="net.sf.jasperreports.export.xls.ignore.graphics" value="false"/>
	<property name="net.sf.jasperreports.exports.xls.font.size.fix.enabled" value="false"/>
	<property name="net.sf.jasperreports.export.xls.remove.empty.space.between.columns" value="false"/>
	<property name="net.sf.jasperreports.export.xls.remove.empty.space.between.rows" value="false"/>
	<property name="com.jaspersoft.studio.data.defaultdataadapter" value="Sample DB"/>
	<style name="Table_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 1_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 1_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 1_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 2_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 2_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 2_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 3_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 3_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table 3_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<subDataset name="dichiarazioneConsumi" uuid="878aa02e-f5d0-46db-95ab-ebf65634f7ea">
		<property name="com.jaspersoft.studio.data.sql.tables" value=""/>
		<property name="com.jaspersoft.studio.data.defaultdataadapter" value="Sample DB"/>
		<parameter name="anno" class="java.lang.String"/>
		<parameter name="id_anag" class="java.lang.Long"/>
		<queryString>
			<![CDATA[select provincia_erogazione, data_presentazione, usi_industriali_1200, usi_industriali_up, tot_industriali,
	usi_civili_120, usi_civili_480, usi_civili_1560, usi_civili_up, tot_civili, tot_nuovi_allacciamenti,
	conguaglio_dich,  
	sdc.conguaglio_calcolato - (select (case when sum(sdv.importo ) is null then 0 else sum(sdv.importo ) end) 
								from sigas_dich_consumi sdcc right join sigas_dich_versamenti sdv 
										on sdv.id_consumi = sdcc.id_consumi  
								where sdcc.annualita = $P{anno}  and sdcc.id_anag = $P{id_anag}  
								and sdc.provincia_erogazione = (select sp.sigla_provincia from sigas_provincia sp where sp.id_provincia = sdv.fk_provincia )  ) conguaglio_calcolato, 
	rettifiche, arrotondamenti, totale_calcolato 
	from sigas_dich_consumi sdc 
	where sdc.annualita =  $P{anno} and sdc.id_anag = $P{id_anag} and sdc.mod_id_consumi = 0 order by sdc.provincia_erogazione]]>
		</queryString>
		<field name="provincia_erogazione" class="java.lang.String">
			<property name="com.jaspersoft.studio.field.label" value="provincia_erogazione"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="data_presentazione" class="java.sql.Timestamp">
			<property name="com.jaspersoft.studio.field.label" value="data_presentazione"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_industriali_1200" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_industriali_1200"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_industriali_up" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_industriali_up"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="tot_industriali" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="tot_industriali"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_120" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_120"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_480" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_480"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_1560" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_1560"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="usi_civili_up" class="java.lang.Integer">
			<property name="com.jaspersoft.studio.field.label" value="usi_civili_up"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="tot_civili" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="tot_civili"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="tot_nuovi_allacciamenti" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="tot_nuovi_allacciamenti"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="conguaglio_dich" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="conguaglio_dich"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="conguaglio_calcolato" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="conguaglio_calcolato"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="rettifiche" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="rettifiche"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="arrotondamenti" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="arrotondamenti"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
		<field name="totale_calcolato" class="java.math.BigDecimal">
			<property name="com.jaspersoft.studio.field.label" value="totale_calcolato"/>
			<property name="com.jaspersoft.studio.field.tree.path" value="sigas_dich_consumi"/>
		</field>
	</subDataset>
	<parameter name="anno" class="java.lang.String"/>
	<parameter name="id_anag" class="java.lang.Long"/>
	<queryString>
		<![CDATA[select a.denominazione as denominazione, sum(usi_industriali_1200) as sum_ind_1200, sum(usi_industriali_up) as sum_ind_up, sum(tot_industriali) as sum_ind,
	sum(usi_civili_120) as sum_civ_120, sum(usi_civili_480) as sum_civ_480, sum(usi_civili_1560) as sum_civ_1560, sum(usi_civili_up) as sum_civ_up, sum(tot_civili) as sum_civ, sum(tot_nuovi_allacciamenti) as sum_all,
	sum(conguaglio_dich) as sum_cong_dich, sum(conguaglio_calcolato) - (select (case when sum(sdv.importo ) is null then 0 else sum(sdv.importo ) end) 
								from sigas_dich_consumi sdcc right join sigas_dich_versamenti sdv 
										on sdv.id_consumi = sdcc.id_consumi  
								where sdcc.annualita = $P{anno}  and sdcc.id_anag = $P{id_anag}   )  as sum_cong_calc, sum(rettifiche) as sum_rett, sum(arrotondamenti) as sum_arr, sum(totale_calcolato) as sum_tot from sigas_dich_consumi c, sigas_anagrafica_soggetti a 
	where annualita = $P{anno} and c.id_anag = $P{id_anag} and mod_id_consumi = 0 and a.id_anag = $P{id_anag}
	group by a.denominazione]]>
	</queryString>
	<field name="denominazione" class="java.lang.String"/>
	<field name="sum_ind_1200" class="java.lang.Integer"/>
	<field name="sum_ind_up" class="java.lang.Integer"/>
	<field name="sum_ind" class="java.math.BigDecimal"/>
	<field name="sum_civ_120" class="java.lang.Integer"/>
	<field name="sum_civ_480" class="java.lang.Integer"/>
	<field name="sum_civ_1560" class="java.lang.Integer"/>
	<field name="sum_civ_up" class="java.lang.Integer"/>
	<field name="sum_civ" class="java.math.BigDecimal"/>
	<field name="sum_all" class="java.math.BigDecimal"/>
	<field name="sum_cong_dich" class="java.math.BigDecimal"/>
	<field name="sum_cong_calc" class="java.math.BigDecimal"/>
	<field name="sum_rett" class="java.math.BigDecimal"/>
	<field name="sum_arr" class="java.math.BigDecimal"/>
	<field name="sum_tot" class="java.math.BigDecimal"/>
	<variable name="imgPiemonte" class="java.lang.String">
		<variableExpression><![CDATA["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"]]></variableExpression>
	</variable>
	<columnHeader>
		<band height="280">
			<frame>
				<reportElement x="0" y="0" width="960" height="110" uuid="a5dc679a-1d12-4ebf-8584-785e75d6ff24"/>
				<image evaluationTime="Report">
					<reportElement x="0" y="0" width="360" height="60" uuid="35603c9e-801b-4bb5-8b11-5c2004529667"/>
					<imageExpression><![CDATA[javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer($V{imgPiemonte})))]]></imageExpression>
				</image>
				<staticText>
					<reportElement x="0" y="60" width="360" height="15" uuid="bcc2eb00-281b-47be-9adb-6cd0068b6b65"/>
					<text><![CDATA[Direzione Risorse Finanziarie e Patrimonio]]></text>
				</staticText>
				<staticText>
					<reportElement x="0" y="75" width="360" height="15" uuid="48cec07b-685c-4ecf-934e-2c25bac72e72">
						<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
					</reportElement>
					<text><![CDATA[Settore Politiche Fiscali e Contenzioso amministrativo]]></text>
				</staticText>
				<staticText>
					<reportElement x="0" y="90" width="360" height="20" uuid="ce4d62ee-aa2f-494a-9834-e0d4e7142d4e"/>
					<text><![CDATA[settore.tributi@regione.piemonte.it]]></text>
				</staticText>
			</frame>
			<staticText>
				<reportElement x="0" y="130" width="1980" height="30" uuid="84c5a6e2-172f-45aa-8ed1-b15b80ea7275"/>
				<textElement textAlignment="Center" verticalAlignment="Middle">
					<font size="22"/>
				</textElement>
				<text><![CDATA[Sistema informativo Gas Naturale]]></text>
			</staticText>
			<staticText>
				<reportElement x="0" y="187" width="1980" height="30" uuid="934eb491-17bc-4ef5-94c4-1a160dad06cd"/>
				<textElement textAlignment="Center" verticalAlignment="Middle">
					<font size="20"/>
				</textElement>
				<text><![CDATA[Dettagli dichiarazione consumi]]></text>
			</staticText>
		</band>
	</columnHeader>
	<detail>
		<band height="95" splitType="Stretch">
			<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
			<textField>
				<reportElement x="0" y="35" width="1980" height="30" isRemoveLineWhenBlank="true" uuid="ed12b3eb-3b1f-45c1-aa26-c0a425bb0308">
					<printWhenExpression><![CDATA[$P{anno} != null]]></printWhenExpression>
				</reportElement>
				<textElement textAlignment="Left" verticalAlignment="Middle">
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["Soggetto dichiarante:  " + $F{denominazione}]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="0" y="1" width="240" height="30" isRemoveLineWhenBlank="true" uuid="120c2b25-5a21-4170-ad94-2739f07e1dbb">
					<printWhenExpression><![CDATA[$P{anno} != null]]></printWhenExpression>
				</reportElement>
				<textElement textAlignment="Left" verticalAlignment="Middle">
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["Anno Dichiarazione:  " + $P{anno}]]></textFieldExpression>
			</textField>
		</band>
		<band height="66">
			<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
			<componentElement>
				<reportElement key="" isPrintRepeatedValues="false" x="0" y="-1" width="1980" height="61" uuid="e15d6a63-1968-413d-91b5-53b9c4298d16">
					<property name="com.jaspersoft.studio.layout" value="com.jaspersoft.studio.editor.layout.VerticalRowLayout"/>
					<property name="com.jaspersoft.studio.table.style.table_header" value="Table 3_TH"/>
					<property name="com.jaspersoft.studio.table.style.column_header" value="Table 3_CH"/>
					<property name="com.jaspersoft.studio.table.style.detail" value="Table 3_TD"/>
					<property name="net.sf.jasperreports.export.headertoolbar.table.name" value="dichiarazioni"/>
				</reportElement>
				<jr:table xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd" whenNoDataType="AllSectionsNoDetail">
					<datasetRun subDataset="dichiarazioneConsumi" uuid="2bf38382-8ac0-4f92-975a-7f5e90ec1e48">
						<datasetParameter name="anno">
							<datasetParameterExpression><![CDATA[$P{anno}]]></datasetParameterExpression>
						</datasetParameter>
						<datasetParameter name="id_anag">
							<datasetParameterExpression><![CDATA[$P{id_anag}]]></datasetParameterExpression>
						</datasetParameter>
						<connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>
					</datasetRun>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna1"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Provincia]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{provincia_erogazione}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="49746f88-33e7-40a7-9c8d-69a9e0fb6038">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna2"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement stretchType="RelativeToTallestObject" x="0" y="0" width="120" height="30" uuid="0a8f65e8-58ce-491a-8b1a-e2f012bdfab5">
									<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
								</reportElement>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Data]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="dd/MM/yyyy" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f6c4633e-53cf-4f53-8ef1-6813d4239313"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{data_presentazione}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="9ae99573-011d-45cc-82fa-0691ab4bb66e">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna3"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="a2c5feef-315a-4fbc-b943-1f1f3ffab59b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Industriale 1200]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="1046e737-fcc3-4c57-9f5c-fddaa2d8415e"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_industriali_1200}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="23f9cd33-5a38-4507-baa3-78de414adbc2">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna4"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="5345aca6-d422-4ec5-9031-68b838db858b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Industriale > 1200]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="9bb5b062-4c79-4b31-9441-b2e21b03f08f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_industriali_up}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="f30c151f-33ea-4f93-be7c-c485265b3bbe">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna5"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="5f048843-38a7-4d59-939c-1c80a6874995"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale Industriale]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="2a85df3a-bbbb-457e-b775-80300545c01c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{tot_industriali}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="0151c8bd-0147-4319-8834-1cde34566c22">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna6"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="0f3103b9-c477-41d5-9a52-56b78f05c2ed"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile 120]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f64a77e6-9efa-49e8-853c-6e9ba5a16e9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_120}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="c73c8084-d6c8-4199-a52b-ca65b55ecc82">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna7"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="7108b3b8-1fbe-4c14-a7a6-1f241e2f0d93"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile 480]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f6c870db-8b12-4845-82ff-a0d2b5554693"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_480}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="b72e02cd-face-49f2-813d-fe2785864c9e">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna8"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="35829659-8dc9-4131-9327-d5b3de8214e8"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile 1560]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="65a6b417-8280-430b-9d2d-b447b8b74956"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_1560}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna9"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Civile > 1560]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{usi_civili_up}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna10"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale Civile]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{tot_civili}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna11"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale nuovi allac.]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{tot_nuovi_allacciamenti}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna12"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Conguaglio dich.]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{conguaglio_dich}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna13"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Conguaglio calc.]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{conguaglio_calcolato}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna14"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Rettifiche]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{rettifiche}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna15"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Arrotondamenti]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{arrotondamenti}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="120" uuid="5d6bf5be-cf92-4ae9-ae01-a215ebd1d7e4">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna16"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="120" height="30" uuid="52399b0e-ede0-4756-81ee-6995b6817b0c"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Totale]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 €" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="120" height="30" uuid="f94df620-e3c6-43a8-a467-f667749aaf9b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{totale_calcolato}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
				</jr:table>
			</componentElement>
		</band>
	</detail>
	<columnFooter>
		<band height="45">
			<property name="com.jaspersoft.studio.unit.height" value="px"/>
			<textField pattern="#,##0">
				<reportElement x="240" y="0" width="120" height="20" uuid="66b1e0bc-7a71-4c09-8317-ec898ab911d3"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_ind_1200}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement x="360" y="0" width="120" height="20" uuid="2b4de619-be5d-42ba-a725-a81f4fae0b07"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_ind_up}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="480" y="0" width="120" height="20" uuid="b4980843-2a0b-475b-891a-3a4284640ea6"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_ind}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement x="601" y="0" width="120" height="20" uuid="a081048a-6746-49b4-8885-f77755bc8251"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_120}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement x="721" y="0" width="120" height="20" uuid="13d85505-9199-4e21-87a7-218b9e51af69"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_480}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement x="841" y="0" width="119" height="20" uuid="97eb99e2-dd3c-405f-aa0c-72cccbf9cdce"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_1560}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0">
				<reportElement x="960" y="0" width="120" height="20" uuid="64ee9a4b-8146-4c3d-99cf-53c420224b65"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ_up}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1080" y="0" width="120" height="20" uuid="467d1b70-3e31-459c-8df9-0e4501c094ab"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_civ}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1200" y="0" width="120" height="20" uuid="9e855fcc-9683-421b-92a3-0e460849efe6"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_all}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1320" y="0" width="120" height="20" uuid="73ed4801-c107-4064-8dec-2a916e41ed6f"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_cong_dich}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1440" y="0" width="120" height="20" uuid="497d4c9e-1403-49ea-a289-e90157b6ad32"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_cong_calc}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1560" y="0" width="120" height="20" uuid="a9a32e68-dc6a-4c89-9f3f-4bb3b58571f0"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_rett}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1680" y="0" width="120" height="20" uuid="a3b5d25e-588d-4e12-9c84-627638e6f684"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_arr}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00 €">
				<reportElement x="1800" y="0" width="120" height="20" uuid="6ebfc9e9-53ad-4d96-8775-2aef6c3ce6ef"/>
				<textElement textAlignment="Center">
					<font size="11"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{sum_tot}]]></textFieldExpression>
			</textField>
		</band>
	</columnFooter>
</jasperReport>'as xml), jasper=NULL
WHERE id_report=2;