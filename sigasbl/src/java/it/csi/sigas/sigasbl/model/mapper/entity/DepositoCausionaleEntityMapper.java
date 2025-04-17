package it.csi.sigas.sigasbl.model.mapper.entity;

import java.util.List;

import it.csi.sigas.sigasbl.model.entity.SigasDepositoCausionale;
import it.csi.sigas.sigasbl.model.mapper.EntityMapper;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;

public interface DepositoCausionaleEntityMapper	extends EntityMapper<SigasDepositoCausionale, DepositoCausionaleVO> {
	
	public List<SigasDepositoCausionale> mapListVOToListEnttity(List<DepositoCausionaleVO> vn);

}
