package dio.santander.bootcamp.controller;

import dio.santander.bootcamp.model.Pedido;
import dio.santander.bootcamp.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        pedido.setData(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        return pedidoRepository.findById(id)
                .map(p -> {
                    p.setData(LocalDateTime.now());
                    p.setProdutos(pedido.getProdutos());
                    return ResponseEntity.ok(pedidoRepository.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(p -> {
                    pedidoRepository.delete(p);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
