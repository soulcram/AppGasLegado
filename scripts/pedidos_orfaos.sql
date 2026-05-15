-- Pedidos sem vínculo com cliente (ID_CLIENTEp NULL ou inválido)
-- Executar no Derby: jdbc:derby:C:\BancoDeDados\ProgramaGas

SELECT ID_PEDIDO, DIA, ID_CLIENTEp, PEDIDO, STATUS, VALOR, OBSERVACAO
FROM PEDIDOS
WHERE ID_CLIENTEp IS NULL
ORDER BY DIA DESC, ID_PEDIDO DESC;

-- Contagem por dia (útil para priorizar correção manual)
SELECT DIA, COUNT(*) AS QTD_ORFAOS
FROM PEDIDOS
WHERE ID_CLIENTEp IS NULL
GROUP BY DIA
ORDER BY DIA DESC;

-- Clientes cadastrados hoje sem pedidos vinculados (investigação)
-- Ajuste a data conforme necessário:
-- SELECT c.ID_CLIENTE, c.TELEFONE, c.NOME
-- FROM CLIENTES c
-- LEFT JOIN PEDIDOS p ON p.ID_CLIENTEp = c.ID_CLIENTE
-- WHERE p.ID_PEDIDO IS NULL;
