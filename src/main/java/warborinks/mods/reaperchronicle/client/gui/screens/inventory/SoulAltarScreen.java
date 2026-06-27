package warborinks.mods.reaperchronicle.client.gui.screens.inventory;

import javax.annotation.Nonnull;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.network.SoulAltarExtractItemPayload;
import warborinks.mods.reaperchronicle.network.SoulAltarShiftPagePayload;
import warborinks.mods.reaperchronicle.network.SoulAltarSortPayload;
import warborinks.mods.reaperchronicle.world.inventory.SoulAltarMenu;
import warborinks.mods.reaperchronicle.world.level.block.entity.SoulAltar;

@OnlyIn(Dist.CLIENT)
public class SoulAltarScreen extends AbstractContainerScreen<SoulAltarMenu> {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, "textures/gui/container/soul_altar.png"
    );

    private ShiftPageButton[] crystalShiftPageButtons;
    private ShiftPageButton[] reaperShiftPageButtons;
    private ShiftPageButton[] otherShiftPageButtons;
    private SortButton sortButton;

    private Slot lastClickSlot = null;
    private long lastClickTime;
    private boolean doubleclick;

    public SoulAltarScreen(SoulAltarMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);

        imageWidth = 176;
        imageHeight = 178;
        inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        clearWidgets();

        this.crystalShiftPageButtons = this.createShiftPageButtons(SoulAltar.ListType.CRYSTAL, 19);
        this.reaperShiftPageButtons = this.createShiftPageButtons(SoulAltar.ListType.REAPER, 43);
        this.otherShiftPageButtons = this.createShiftPageButtons(SoulAltar.ListType.OTHER, 67);

        this.addShiftPageButtons(this.crystalShiftPageButtons);
        this.addShiftPageButtons(this.reaperShiftPageButtons);
        this.addShiftPageButtons(this.otherShiftPageButtons);

        this.sortButton = new SortButton(leftPos + 133, topPos + 67, 12, this);
        addRenderableWidget(this.sortButton);
    }

    private ShiftPageButton[] createShiftPageButtons(SoulAltar.ListType type, int y) {
        return new ShiftPageButton[]{
            new ShiftPageButton(
                leftPos + 28, topPos + y, 12,
                type, false, this
            ),
            new ShiftPageButton(
                leftPos + 118, topPos + y, 12,
                type, true, this
            )
        };
    }

    @SuppressWarnings("null")
    private void addShiftPageButtons(ShiftPageButton[] buttons) {
        for (ShiftPageButton btn : buttons) {
            addRenderableWidget(btn);
        }
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    @SuppressWarnings("null")
    protected void renderBg(@Nonnull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Slot slot = this.findSlot(mouseX, mouseY);
        if (slot instanceof SoulAltarMenu.DisplaySlot) {
            long now = Util.getMillis();
            this.doubleclick = this.lastClickSlot == slot && now - this.lastClickTime < 250L;
            this.lastClickSlot = slot;
            this.lastClickTime = now;
            
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Slot slot = this.findSlot(mouseX, mouseY);
        if (slot instanceof SoulAltarMenu.DisplaySlot displaySlot) {
            ItemStack stack = displaySlot.getItem();

            if (!stack.isEmpty()) {
                int amount = this.doubleclick ? stack.getMaxStackSize() : 1;
                PacketDistributor.sendToServer(
                    new SoulAltarExtractItemPayload(displaySlot.getType(), displaySlot.getIndex(), amount)
                );
            }
            
            this.doubleclick = false;
            this.lastClickTime = 0L;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    private Slot findSlot(double mouseX, double mouseY) {
        for (int i = 0; i < menu.slots.size(); i++) {
            Slot slot = menu.slots.get(i);
            if (isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY) && slot.isActive()) {
                return slot;
            }
        }

        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public static final class ShiftPageButton extends AbstractButton {
        private static final Component LEFT_TEXT = Component.translatable("gui.reaperchronicle.soul_altar.left_text");
        private static final Component RIGHT_TEXT = Component.translatable("gui.reaperchronicle.soul_altar.right_text");

        private static final ResourceLocation LEFT_SHIFT = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, "container/soul_altar/left_shift"
        );
        private static final ResourceLocation RIGHT_SHIFT = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, "container/soul_altar/right_shift"
        );

        private final SoulAltar.ListType type;
        private final boolean right;
        private final SoulAltarScreen screen;
        
        public ShiftPageButton(int x, int y, int size,
            SoulAltar.ListType type, boolean right, SoulAltarScreen screen
        ) {
            super(
                x, y, size, size,
                (right ? getRightText() : getLeftText())
            );
            
            this.type = type;
            this.right = right;
            this.screen = screen;
        }

        public static Component getLeftText() {
            return LEFT_TEXT.copy();
        }
        public static Component getRightText() {
            return RIGHT_TEXT.copy();
        }
    
        @Override
        public void onPress() {
            if (this.isPageShiftPossible()) {
                PacketDistributor.sendToServer(new SoulAltarShiftPagePayload(this.type, this.right));
            }
        }
    
        @Override
        @SuppressWarnings("null")
        protected void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            if (this.isPageShiftPossible()) {
                guiGraphics.blitSprite(
                    (this.right ? RIGHT_SHIFT : LEFT_SHIFT),
                    getX(), getY(), width, height
                );

                if (isHovered) {
                    guiGraphics.renderTooltip(this.screen.font, getMessage(), mouseX, mouseY);
                }
            }
        }
    
        private boolean isPageShiftPossible() {
            SoulAltar soulAltar = this.screen.getMenu().getSoulAltar();
            int maxPage = soulAltar.getMaxPageForType(type);
            int current = soulAltar.getPageForType(type);
            if (this.right) {
                return current < maxPage;
            } else {
                return current > 0;
            }
        }

        @Override
        protected void updateWidgetNarration(@Nonnull NarrationElementOutput narration) {
            defaultButtonNarrationText(narration);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class SortButton extends AbstractButton {
        private static final Component TEXT = Component.translatable("gui.reaperchronicle.soul_altar.sort");

        private static final ResourceLocation SORT = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, "container/soul_altar/sort"
        );

        private final SoulAltarScreen screen;

        public SortButton(int x, int y, int size, SoulAltarScreen screen) {
            super(x, y, size, size, getText());

            this.screen = screen;
        }

        public static Component getText() {
            return TEXT.copy();
        }

        @Override
        public void onPress() {
            PacketDistributor.sendToServer(new SoulAltarSortPayload());
        }

        @Override
        @SuppressWarnings("null")
        protected void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.blitSprite(SORT, getX(), getY(), width, height);
            if (isHovered) {
                guiGraphics.renderTooltip(this.screen.font, getMessage(), mouseX, mouseY);
            }
        }

        @Override
        protected void updateWidgetNarration(@Nonnull NarrationElementOutput narration) {
            defaultButtonNarrationText(narration);
        }
    }
}
