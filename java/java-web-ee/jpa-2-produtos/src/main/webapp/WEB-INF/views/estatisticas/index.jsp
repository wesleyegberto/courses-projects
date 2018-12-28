<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="../template/top.jsp" />
<div class="col-sm-8">
	<div class="panel panel-default">
		<div class="panel-heading">Estatísticas</div>
		<div class="panel-body">
			<div class="container">
				<div class="col-sm-12">
					<a href="<c:url value="/estatisticas/limpar"/>">Limpar</a>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th></th>
								<th colspan="2">Query</th>
								<th colspan="3">Transações</th>
								<th colspan="2">1st Cache</th>
								<th colspan="2">2nd Cache</th>
							</tr>
							<tr>
								<th>Conexões</th>
								<th>Qtde</th>
								<th>Lowest (ms)</th>
								<th>Commitadas</th>
								<th>Optimisc Lock</th>
								<th>Commit + Rollback</th>
								<th>Hit</th>
								<th>Miss</th>
								<th>Hit</th>
								<th>Miss</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${statistics.connectCount}</td>
								<td>${statistics.queryExecutionCount}</td>
								<td>${statistics.queryExecutionMaxTime}</td>
								<td>${statistics.successfulTransactionCount}</td>
								<td>${statistics.optimisticFailureCount}</td>
								<td>${statistics.transactionCount}</td>
								<td>${statistics.queryCacheHitCount}</td>
								<td>${statistics.queryCacheMissCount}</td>
								<td>${statistics.secondLevelCacheHitCount}</td>
								<td>${statistics.secondLevelCacheMissCount}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<c:import url="../template/down.jsp" />
