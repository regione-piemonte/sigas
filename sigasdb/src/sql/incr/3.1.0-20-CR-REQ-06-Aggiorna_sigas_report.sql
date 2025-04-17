update sigas_report set jasper = null, data_update = null
where cod_report like 'EXCEL_CARRELLO_PAGAMENTI';

update sigas_report 
set jasper = null,
jrxml = CAST('<!-- Created with Jaspersoft Studio version 6.20.6.final utlizzo versione della libreria JasperReports6.20.6-5c96b6aa8a39ac1dc6b6bea4b81168e16dd39231  -->
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="Blank_A4" pageWidth="700" pageHeight="850" columnWidth="660" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" isIgnorePagination="true" uuid="8d082e85-c8d4-47f9-a6e1-e856838f7c07">
	<property name="com.jaspersoft.studio.data.defaultdataadapter" value="Sample DB"/>
	<property name="com.jaspersoft.studio.unit." value="pixel"/>
	<property name="com.jaspersoft.studio.unit.pageHeight" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.pageWidth" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.topMargin" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.bottomMargin" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.leftMargin" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.rightMargin" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.columnWidth" value="pixel"/>
	<property name="com.jaspersoft.studio.unit.columnSpacing" value="pixel"/>
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
	<subDataset name="AnagraficaSoggetti" uuid="406f4309-c7de-44d9-909d-cb652cbb978e">
		<queryString language="SQL">
			<![CDATA[]]>
		</queryString>
		<field name="subjectName" class="java.lang.String"/>
		<field name="area" class="java.lang.String"/>
		<field name="typeDesc" class="java.lang.String"/>
		<field name="amount" class="java.lang.String"/>
		<field name="monthDesc" class="java.lang.String"/>
		<field name="paymentCode" class="java.lang.String"/>
		<field name="note" class="java.lang.String"/>
		<field name="status" class="java.lang.String"/>
		<field name="currentDate" class="java.lang.String"/>
		<field name="payDate" class="java.lang.String"/>
		<field name="statusDescrizione" class="java.lang.String"/>
	</subDataset>
	<parameter name="soggetti" class="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource"/>
	<parameter name="anno" class="java.lang.String"/>
	<parameter name="denominazioneAzienda" class="java.lang.String"/>
	<queryString>
		<![CDATA[select count(*) from sigas_carrello_pagamenti a]]>
	</queryString>
	<variable name="imgPiemonte" class="java.lang.String">
		<variableExpression><![CDATA["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"]]></variableExpression>
	</variable>
	<background>
		<band splitType="Stretch"/>
	</background>
	<title>
		<band height="280" splitType="Stretch">
			<frame>
				<reportElement x="-30" y="0" width="680" height="110" uuid="80bb47f2-b467-41a0-86cd-1b5082eaa5fd">
					<property name="com.jaspersoft.studio.unit.x" value="px"/>
					<property name="com.jaspersoft.studio.unit.y" value="px"/>
				</reportElement>
				<image evaluationTime="Report">
					<reportElement x="0" y="0" width="360" height="60" uuid="0bf4d68f-80de-4a13-a403-ad6686e9527f"/>
					<imageExpression><![CDATA[javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer($V{imgPiemonte})))]]></imageExpression>
				</image>
				<staticText>
					<reportElement x="0" y="60" width="360" height="15" uuid="4475d166-59ed-4aed-ac84-e57c4d6c61b0"/>
					<text><![CDATA[Direzione Risorse Finanziarie e Patrimonio]]></text>
				</staticText>
				<staticText>
					<reportElement x="0" y="75" width="360" height="15" uuid="a74c800c-1275-453e-8484-c3ccec1e5638">
						<property name="com.jaspersoft.studio.unit.height" value="pixel"/>
					</reportElement>
					<text><![CDATA[Settore Politiche Fiscali e Contenzioso amministrativo]]></text>
				</staticText>
				<staticText>
					<reportElement x="0" y="90" width="360" height="20" uuid="eb15a7b3-54c7-4112-b327-66edaf0c4ab9"/>
					<text><![CDATA[settore.tributi@regione.piemonte.it]]></text>
				</staticText>
			</frame>
			<staticText>
				<reportElement x="0" y="130" width="650" height="30" uuid="5284b004-7816-4380-beb6-6c2b5255f1d5"/>
				<textElement textAlignment="Center" verticalAlignment="Middle">
					<font size="22"/>
				</textElement>
				<text><![CDATA[Sistema informativo Gas Naturale]]></text>
			</staticText>
			<staticText>
				<reportElement x="0" y="180" width="170" height="20" uuid="85a7ba90-518b-40d4-bb85-e10cd26db61d"/>
				<textElement>
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<text><![CDATA[Denominazione azienda:]]></text>
			</staticText>
			<textField>
				<reportElement x="170" y="182" width="480" height="18" uuid="41965c72-15d2-4eac-badb-db07fd70791d"/>
				<textElement>
					<font fontName="SansSerif" size="14" isBold="false"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{denominazioneAzienda}]]></textFieldExpression>
			</textField>
			<staticText>
				<reportElement x="1" y="248" width="170" height="20" uuid="cdbbd886-9111-4585-b35c-e2bba2deda15"/>
				<textElement>
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<text><![CDATA[Elenco pagamenti]]></text>
			</staticText>
			<staticText>
				<reportElement x="1" y="202" width="170" height="20" uuid="c7ac809b-76e4-4cc3-8f8a-415e6db9517c"/>
				<textElement>
					<font fontName="SansSerif" size="14" isBold="true"/>
				</textElement>
				<text><![CDATA[Anno:]]></text>
			</staticText>
			<textField>
				<reportElement x="172" y="203" width="478" height="18" uuid="32581f97-3faf-42aa-9eee-3a79e7370173"/>
				<textElement>
					<font fontName="SansSerif" size="14"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{anno}]]></textFieldExpression>
			</textField>
		</band>
	</title>
	<detail>
		<band height="60" splitType="Stretch">
			<componentElement>
				<reportElement key="" isPrintRepeatedValues="false" x="0" y="-11" width="650" height="61" uuid="65335ab6-3287-4d3c-97ef-9b20e7075c66">
					<property name="com.jaspersoft.studio.layout" value="com.jaspersoft.studio.editor.layout.VerticalRowLayout"/>
					<property name="com.jaspersoft.studio.table.style.table_header" value="Table 3_TH"/>
					<property name="com.jaspersoft.studio.table.style.column_header" value="Table 3_CH"/>
					<property name="com.jaspersoft.studio.table.style.detail" value="Table 3_TD"/>
					<property name="net.sf.jasperreports.export.headertoolbar.table.name" value="dichiarazioni"/>
				</reportElement>
				<jr:table xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd" whenNoDataType="AllSectionsNoDetail">
					<datasetRun subDataset="AnagraficaSoggetti" uuid="2bf38382-8ac0-4f92-975a-7f5e90ec1e48">
						<dataSourceExpression><![CDATA[$P{soggetti}]]></dataSourceExpression>
					</datasetRun>
					<jr:column width="160" uuid="40e73667-9924-4608-9f6f-3626ba2a4b26">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna1"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="160" height="30" uuid="22764051-408e-4ca6-9c80-99fe396ebee2"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Codice]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="160" height="30" uuid="1e98fb22-45a1-4321-8166-09ca95479104"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{paymentCode}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="100" uuid="e13e9875-89d6-4bfa-8e49-bdf83443def9">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna2"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="100" height="30" uuid="b5871b0b-9b06-4408-994a-bc441bda6ab6"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Stato]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 â‚¬" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="100" height="30" uuid="f3ff7f8e-286b-4d9e-acfe-050cb1b0e3ae"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{statusDescrizione}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="100" uuid="e13e9875-89d6-4bfa-8e49-bdf83443def0">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna3"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="100" height="30" uuid="b5871b0b-9b06-4408-994a-bc441bda6ab6"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Mese]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 â‚¬" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="100" height="30" uuid="f3ff7f8e-286b-4d9e-acfe-050cb1b0e3ae"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{monthDesc}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="100" uuid="cea4a651-4bf2-4ae1-83a4-876c0407d8fa">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna4"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement stretchType="RelativeToTallestObject" x="0" y="0" width="100" height="30" uuid="700590e0-d936-40e4-bfee-b8295cd32963">
									<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
								</reportElement>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Provincia]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField isBlankWhenNull="true">
								<reportElement x="0" y="0" width="100" height="30" uuid="c5677c7b-4756-4114-9d9a-605c5782a38b"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{area}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="190" uuid="810e2acf-a16d-467e-ab63-26e2fb09d14f">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna5"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="190" height="30" uuid="d6dde60d-25ab-4c70-9412-6bb214d8ab1f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Importo]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField isBlankWhenNull="true">
								<reportElement x="0" y="0" width="190" height="30" uuid="b8ecfd22-24b2-46b7-b402-741505463a1f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{amount}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="190" uuid="e13e9875-89d6-4bfa-8e49-bdf83443def0">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna6"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="190" height="30" uuid="b5871b0b-9b06-4408-994a-bc441bda6ab6"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Tipo pagamento]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField pattern="#,##0.00 â‚¬" isBlankWhenNull="true">
								<reportElement x="0" y="0" width="190" height="30" uuid="f3ff7f8e-286b-4d9e-acfe-050cb1b0e3ae"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{typeDesc}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="190" uuid="820e2acf-a16d-467e-ab63-741505463a1f">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna7"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="190" height="30" uuid="d6dde60d-25ab-4c70-9412-6bb214d8ab1f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Note]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField isBlankWhenNull="true">
								<reportElement x="0" y="0" width="190" height="30" uuid="b8ecfd22-24b2-46b7-b402-741505463a1f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{note}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="190" uuid="810e2acf-a16d-467e-ab63-6bb214d8ab1f">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Colonna8"/>
						<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
						<jr:tableHeader style="Table 3_TH" height="30" rowSpan="1">
							<property name="com.jaspersoft.studio.unit.width" value="px"/>
							<staticText>
								<reportElement x="0" y="0" width="190" height="30" uuid="d6dde60d-25ab-4c70-9412-6bb214d8ab1f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<text><![CDATA[Data pagamento]]></text>
							</staticText>
						</jr:tableHeader>
						<jr:detailCell style="Table 3_TD" height="30">
							<textField isBlankWhenNull="true">
								<reportElement x="0" y="0" width="190" height="30" uuid="b8ecfd22-24b2-46b7-b402-741505463a1f"/>
								<textElement textAlignment="Center" verticalAlignment="Middle">
									<font fontName="SansSerif" size="11"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{payDate}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
				</jr:table>
			</componentElement>
		</band>
	</detail>
</jasperReport>' AS XML)
where id_report in (select id_report 
					from sigas_report 
					where cod_report like 'EXCEL_CARRELLO_PAGAMENTI');