package com.cibertec.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	
	/*@Query("select p from Proveedor p where (p.razonsocial like ?1) and (?2 is '' or p.ruc = ?2) "
			+ "and (p.estado = ?3) and (?4 is -1 or p.pais.idPais = ?4) ")*/   
	
	@Query(value = "call busca_proveedor_razon_ruc_estado_pais(?1, ?2, ?3, ?4)", nativeQuery = true)
	public List<Proveedor> listaProveedorPorRazonRucEstadoPais(String razonsocial, String ruc, int estado, int idPais );
	
	@Query(value = "call sp_consulta_proveedor_like(?1)", nativeQuery = true)
	public List<Proveedor> listaPorRazonLike(String razonsocial);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "call sp_elimina_proveedor(?1)", nativeQuery = true)
	public void eliminaProveedor(int idProveedor);
	
	@Query(value = "call sp_busca_proveedor(?1)", nativeQuery = true)
	public Proveedor buscaProveedorPorPk(int idProveedor);
	
	@Query(value = "call sp_busca_ultimo_proveedor()", nativeQuery = true)
	public Proveedor buscaUltimoProveedor();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "call sp_inserta_proveedor(?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
	public void insertaProveedor(String razonsocial, String ruc, String direccion, String celular, String contacto, int estado, int idPais);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "call sp_actualiza_proveedor(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
	public void actualizaProveedor(String razonsocial, String ruc, String direccion, String celular, String contacto, int estado, int idPais, int idProveedor);

}
