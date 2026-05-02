package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceImplTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private CategorieProduitRepository categorieProduitRepository;

    @InjectMocks
    private ProduitServiceImpl produitService;

    private Produit produit;
    private Stock stock;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setIdProduit(1L);
        produit.setCodeProduit("P001");
        produit.setLibelleProduit("Produit Test");
        produit.setPrix(100.0f);
        produit.setDateCreation(new Date());

        stock = new Stock("Stock Test", 50, 10);
        stock.setIdStock(1L);
    }

    @Test
    void testRetrieveAllProduits() {
        when(produitRepository.findAll()).thenReturn(Arrays.asList(produit));
        List<Produit> result = produitService.retrieveAllProduits();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("P001", result.get(0).getCodeProduit());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    void testAddProduit() {
        when(produitRepository.save(produit)).thenReturn(produit);
        Produit result = produitService.addProduit(produit);
        assertNotNull(result);
        assertEquals("Produit Test", result.getLibelleProduit());
        assertEquals(100.0f, result.getPrix());
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testUpdateProduit() {
        produit.setPrix(200.0f);
        when(produitRepository.save(produit)).thenReturn(produit);
        Produit result = produitService.updateProduit(produit);
        assertNotNull(result);
        assertEquals(200.0f, result.getPrix());
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testRetrieveProduit() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        Produit result = produitService.retrieveProduit(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdProduit());
        assertEquals("P001", result.getCodeProduit());
    }

    @Test
    void testRetrieveProduitNotFound() {
        when(produitRepository.findById(99L)).thenReturn(Optional.empty());
        Produit result = produitService.retrieveProduit(99L);
        assertNull(result);
    }

    @Test
    void testDeleteProduit() {
        doNothing().when(produitRepository).deleteById(1L);
        produitService.deleteProduit(1L);
        verify(produitRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAssignProduitToStock() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(produitRepository.save(produit)).thenReturn(produit);
        produitService.assignProduitToStock(1L, 1L);
        assertEquals(stock, produit.getStock());
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testProduitEntity() {
        Produit p = new Produit();
        p.setCodeProduit("P002");
        p.setLibelleProduit("Test");
        p.setPrix(50.0f);
        assertEquals("P002", p.getCodeProduit());
        assertEquals(50.0f, p.getPrix());
    }

    @Test
    void testStockEntity() {
        Stock s = new Stock("Stock A", 100, 20);
        assertEquals("Stock A", s.getLibelleStock());
        assertEquals(100, s.getQte());
        assertEquals(20, s.getQteMin());
        assertTrue(s.getQte() > s.getQteMin());
    }
}
